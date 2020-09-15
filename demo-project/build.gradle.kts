import com.android.build.gradle.AppExtension

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
    kotlin("multiplatform") version "1.4.0"
    id("kotlin-ksp") version "1.4.0-dev-experimental-20200828"
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

val mmockRuntimeVersion = "0.0.1-SNAPSHOT"
val mmockKspCodegenVersion = "0.0.2"

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

                implementation("com.pega.mmock:mmock-runtime:$mmockRuntimeVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.8-1.4.0-rc")
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
                //implementation("com.pega.mmock:mmock-compiler-plugin:$mmockKspCodegenVersion")
                configurations["ksp"].dependencies.add(project.dependencies.create("com.pega.mmock:mmock-compiler-plugin:$mmockKspCodegenVersion"))
                //configurations.get("ksp").dependencies.add("com.pega.mmock:mmock-compiler-plugin:$mmockKspCodegenVersion")
                //ksp("com.pega.mmock:mmock-compiler-plugin:$mmockKspCodegenVersion")
                //configurations.get("ksp").dependencies.add(DefaultExternalModuleDependency("com.pega.mmock", "mmock-compiler-plugin", "$mmockKspCodegenVersion"))
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
