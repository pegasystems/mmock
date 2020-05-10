import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("java-gradle-plugin")
    kotlin("kapt")
    `maven-publish`
}

repositories {
    mavenCentral()
}

gradlePlugin {
    plugins {
        creating {
            id = "com.github.virelion.mmock"
            implementationClass = "com.github.virelion.mmock.gradle.MMockPlugin"
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

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}