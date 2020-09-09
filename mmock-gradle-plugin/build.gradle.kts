import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    kotlin("kapt")
    id("java-gradle-plugin")
    id("org.jlleitschuh.gradle.ktlint")
    id("com.gradle.plugin-publish")
    `maven-publish`
}

repositories {
    mavenCentral()
}

val artifactoryURL: String by project
val artifactoryUser: String by project
val artifactoryPassword: String by project
val snapshotRepository: String by project
val releaseRepository: String by project

gradlePlugin {
    plugins {
        register("MMockGradlePlugin") {
            id = "com.pega.mmock"
            implementationClass = "com.pega.mmock.gradle.MMockPlugin"
        }
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("gradle-plugin-api"))
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.72")
    compileOnly("com.google.auto.service:auto-service:1.0-rc6")
    kapt("com.google.auto.service:auto-service:1.0-rc6")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

if (artifactoryURL.isNotEmpty()) {
    publishing {
        repositories {
            val url = if (version.toString().endsWith("SNAPSHOT")) "$artifactoryURL/$snapshotRepository" else "$artifactoryURL/$releaseRepository"
            maven(url = url) {
                credentials {
                    username = artifactoryUser
                    password = artifactoryPassword
                }
            }
        }
    }
}
