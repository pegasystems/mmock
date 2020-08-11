import com.android.build.gradle.AppExtension

buildscript {
    repositories {
        jcenter()
        mavenCentral()
        google()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.5.1")
    }
}

plugins {
    kotlin("multiplatform") version "1.3.72"
    id("org.jlleitschuh.gradle.ktlint") version "9.2.1"
    id("com.pega.mmock") version "0.0.0-SNAPSHOT"
}

configureAndroid()

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
    google()
}

val mmockRuntimeVersion = "0.0.0-SNAPSHOT"

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
    android()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":sub-module"))

                implementation(kotlin("stdlib-common"))
                implementation("com.pega.mmock:mmock-runtime:$mmockRuntimeVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:1.3.5")
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
                implementation(kotlin("stdlib-jdk8"))
                implementation(kotlin("reflect"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.5")
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
            }
        }

        val jsMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.3.5")
            }
        }

        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }

        val nativeMain by creating {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:1.3.5")
            }
        }

        val linuxX64Main by getting {
            dependsOn(nativeMain)
            dependencies {
            }
        }

        val macosX64Main by getting {
            dependsOn(nativeMain)
            dependencies {
            }
        }

        val iosX64Main by getting {
            dependsOn(nativeMain)
            dependencies {
            }
        }

        val iosArm64Main by getting {
            dependsOn(nativeMain)
            dependencies {
            }
        }

        val mingwX64Main by getting {
            dependsOn(nativeMain)
            dependencies {
            }
        }

        val androidMain by getting {
            dependsOn(nativeMain)
            dependencies {
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

fun Project.configureAndroid() {
    apply(plugin = "com.android.application")

    configure<AppExtension> {
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
            java.srcDirs("src/commonMain/kotlin")
            res.srcDirs("src/androidMain/res")
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            assets.srcDirs("src/commonMain/resources/assets")
        }

        sourceSets.getByName("androidTest").apply {
            java.srcDirs("src/commonTest/kotlin")
            res.srcDirs("src/androidTest/res")
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            assets.srcDirs("src/commonMain/resources/assets")
        }

        defaultPublishConfig = "debug"
        testOptions.unitTests.isIncludeAndroidResources = true
    }
}
