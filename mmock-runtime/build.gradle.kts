import com.android.build.gradle.LibraryExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        jcenter()
        mavenCentral()
        google()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.6.2")
    }
}

plugins {
    kotlin("multiplatform")
    `maven-publish`
    id("org.jlleitschuh.gradle.ktlint")
    id("org.jetbrains.dokka") version "1.4.0"
}

val androidEnabled = System.getenv("ANDROID_HOME") != null
if (androidEnabled) {
    configureAndroid()
}

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
    google()
    maven("https://dl.bintray.com/kotlin/kotlin-eap")
}

val coroutinesVersion: String by project
val cglibVersion: String by project
val objenesisVersion: String by project

kotlin {
    jvm()
    js {
        nodejs {
            testTask {
                useMocha()
            }
        }
    }
    mingwX64()
    macosX64()
    linuxX64()
    ios()
    if (androidEnabled) {
        android {
            publishLibraryVariants("release", "debug")
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

                api(project(":mmock-annotations"))
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(kotlin("reflect"))
                implementation("org.objenesis:objenesis:$objenesisVersion")
                implementation("cglib:cglib:$cglibVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
            }
        }

        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }

        val nativeMain by creating {
            dependencies {
            }
        }

        val mingwX64Main by getting {
            dependsOn(nativeMain)
        }

        val macosX64Main by getting {
            dependsOn(nativeMain)
        }

        val linuxX64Main by getting {
            dependsOn(nativeMain)
        }

        val iosX64Main by getting {
            dependsOn(nativeMain)
        }

        val iosArm64Main by getting {
            dependsOn(nativeMain)
        }

        if (androidEnabled) {
            val androidMain by getting {
                dependencies {
                    implementation(kotlin("reflect"))
                    implementation("cglib:cglib:$cglibVersion")
                    implementation("org.objenesis:objenesis:$objenesisVersion")
                    implementation("com.implimentz:unsafe:0.0.6")
                    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                }
            }

            val androidTest by getting {
                dependencies {
                    implementation(kotlin("test"))
                    implementation(kotlin("test-junit"))
                }
            }
        }
    }
}

tasks {
    withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class).all {
        kotlinOptions {
            freeCompilerArgs = freeCompilerArgs + listOf("-Xopt-in=kotlin.RequiresOptIn")
        }
    }

    withType(org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile::class).all {
        kotlinOptions {
            freeCompilerArgs = freeCompilerArgs + listOf("-Xopt-in=kotlin.RequiresOptIn")
        }
    }

    withType(org.jetbrains.kotlin.gradle.tasks.KotlinNativeCompile::class).all {
        kotlinOptions {
            freeCompilerArgs = freeCompilerArgs + listOf("-Xopt-in=kotlin.RequiresOptIn")
        }
    }

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.6"
    }

    withType<org.jetbrains.dokka.gradle.DokkaTask>().configureEach {
        dokkaSourceSets {
            configureEach {
                if (platform.get() == org.jetbrains.dokka.Platform.native) {
                    displayName.set("native")
                }
            }
        }
    }
}

fun Project.configureAndroid() {
    apply(plugin = "com.android.library")

    configure<LibraryExtension> {
        buildToolsVersion("29.0.2")
        compileSdkVersion(29)

        defaultConfig {
            targetSdkVersion(29)
            minSdkVersion(21)
            versionCode = 1
            versionName = "1.0"
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }

        sourceSets.getByName("main").apply {
            java.srcDirs("src/androidMain/kotlin")
            res.srcDirs("src/androidMain/res")
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            assets.srcDirs("src/commonMain/resources/assets")
        }

        sourceSets.getByName("androidTest").apply {
            java.srcDirs("src/commonTest/kotlin", "src/jvmTest/kotlin")
            res.srcDirs("src/androidTest/res")
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            assets.srcDirs("src/commonMain/resources/assets")
        }

        defaultPublishConfig = "debug"
        testOptions.unitTests.isIncludeAndroidResources = true
    }
}
