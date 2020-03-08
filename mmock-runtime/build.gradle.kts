import org.apache.tools.ant.taskdefs.condition.Os

plugins {
    kotlin("multiplatform")
    `maven-publish`
}

kotlin {
    jvm()
    js {
        nodejs {
            testTask { }
        }
    }
    mingwX64()
    macosX64()
    linuxX64()
    ios()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:1.3.3")
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
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3")
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
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.3.3")
            }
        }

        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }

        val nativeMain by creating {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:1.3.3")
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
    }
}

tasks {
    val iosX64Test by creating {
        onlyIf { Os.isFamily(Os.FAMILY_MAC) }

        val device = project.findProperty("iosDevice")?.toString() ?: "iPhone 8"
        val testExecutable = kotlin.targets
                .getByName<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget>("iosX64")
                .binaries
                .getTest("DEBUG")

        dependsOn(testExecutable.linkTaskName)
        group = JavaBasePlugin.VERIFICATION_GROUP
        description = "Runs tests for target 'ios' on an iOS simulator"

        doLast {
            exec {
                commandLine("xcrun", "simctl", "spawn", "--standalone", device, testExecutable.outputFile.absolutePath)
            }
        }
    }
}