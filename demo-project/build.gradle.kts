plugins {
    kotlin("multiplatform") version "1.3.72"
    id("org.jlleitschuh.gradle.ktlint") version "9.2.1"
    id("com.github.virelion.mmock") version "0.0.0-SNAPSHOT"
}

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
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

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":sub-module"))

                implementation(kotlin("stdlib-common"))
                implementation("com.github.virelion.mmock:mmock-runtime:$mmockRuntimeVersion")
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
//                implementation("com.github.virelion.mmock:mmock-runtime-jvm:$mmockRuntimeVersion")
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
//                implementation("com.github.virelion.mmock:mmock-runtime-js:$mmockRuntimeVersion")
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
//                implementation("com.github.virelion.mmock:mmock-runtime-linuxx64:$mmockRuntimeVersion")
            }
        }

        val macosX64Main by getting {
            dependsOn(nativeMain)
            dependencies {
//                implementation("com.github.virelion.mmock:mmock-runtime-macosx64:$mmockRuntimeVersion")
            }
        }

        val iosX64Main by getting {
            dependsOn(nativeMain)
            dependencies {
//                implementation("com.github.virelion.mmock:mmock-runtime-iosx64:$mmockRuntimeVersion")
            }
        }

        val iosArm64Main by getting {
            dependsOn(nativeMain)
            dependencies {
//                implementation("com.github.virelion.mmock:mmock-runtime-iosarm64:$mmockRuntimeVersion")
            }
        }

        val mingwX64Main by getting {
            dependsOn(nativeMain)
            dependencies {
//                implementation("com.github.virelion.mmock:mmock-runtime-mingwx64:$mmockRuntimeVersion")
            }
        }
    }
}
