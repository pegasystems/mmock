import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("java-gradle-plugin")
    kotlin("kapt")
    `maven-publish`
}

group = "com.github.virelion.autojsname"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

gradlePlugin {
    plugins {
        creating {
            id = "com.github.virelion.mmock"
            implementationClass = "com.github.virelion.mmock.gradle.MmockPlugin"
        }
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("gradle-plugin-api"))
    compileOnly("com.google.auto.service:auto-service:1.0-rc6")
    kapt("com.google.auto.service:auto-service:1.0-rc6")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}