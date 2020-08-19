import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.0"
    id("kotlin-ksp") version "1.4.0-rc-dev-experimental-20200814"
    id("com.pega.mmock") version "0.0.1-SNAPSHOT"
}
group = "me.rogok"
version = "1.0-SNAPSHOT"

val mmockRuntimeVersion = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://dl.bintray.com/kotlin/kotlin-eap")
    google()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-ksp:1.4.0-rc-dev-experimental-20200814")
    implementation("com.pega.mmock:mmock-compiler-plugin:4.2.1")
    implementation("com.pega.mmock:mmock-runtime:$mmockRuntimeVersion")
    ksp("com.pega.mmock:mmock-compiler-plugin:4.2.1")
    testImplementation(kotlin("test-junit"))
}
tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}