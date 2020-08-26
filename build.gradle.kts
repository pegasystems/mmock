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
    kotlin("multiplatform") version "1.3.72" apply false
    id("org.jlleitschuh.gradle.ktlint") version "9.2.1" apply false
    id("com.gradle.plugin-publish") version "0.12.0" apply false
    id("org.ajoberstar.reckon") version "0.12.0"
}

reckon {
    scopeFromProp()
    stageFromProp("rc", "beta", "final")
}

tasks.named("reckonTagCreate") {
    dependsOn("check")
}

allprojects {
    repositories {
        mavenCentral()
        jcenter()
        google()
    }
}

tasks.register("printVersion") {
    doLast {
        println("${project.version}")
        println("${project.version}")
    }
}
