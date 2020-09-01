import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.0"
    id("kotlin-ksp") version "1.4.0-dev-experimental-20200828"
    id("com.pega.mmock") version "0.0.1-SNAPSHOT"
}

version = "1.0-SNAPSHOT"

val mmockKspCodegenVersion = "0.0.2"
val mmockRuntimeVersion = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://dl.bintray.com/kotlin/kotlin-eap")
    google()
}

dependencies {
    implementation("com.pega.mmock:mmock-compiler-plugin:$mmockKspCodegenVersion")
    implementation("com.pega.mmock:mmock-runtime:$mmockRuntimeVersion")
    ksp("com.pega.mmock:mmock-compiler-plugin:$mmockKspCodegenVersion")
    testImplementation(kotlin("test-junit"))
}
tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}