buildscript {
    repositories {
        mavenLocal()
        jcenter()
        mavenCentral()
        google()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.6.2")
    }
}

plugins {
    kotlin("multiplatform") version "1.4.21" apply false
    id("org.jlleitschuh.gradle.ktlint") version "9.2.1" apply false
    id("com.gradle.plugin-publish") version "0.12.0" apply false
    id("nebula.release") version "13.0.0"
}

allprojects {
    repositories {
        mavenCentral()
        jcenter()
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
        google()
    }
}

tasks {
    val publishPluginsToMavenLocal by creating {
        dependsOn(
                ":mmock-runtime:publishJvmPublicationToMavenLocal", // required for kspKotlinJvm run
                ":mmock-annotations:publishJvmPublicationToMavenLocal", // required for kspKotlinJvm run
                ":mmock-ksp-plugin:publishToMavenLocal",
                ":mmock-compiler-plugin:publishToMavenLocal",
                ":mmock-compiler-plugin-native:publishToMavenLocal",
                ":mmock-gradle-plugin:publishToMavenLocal"
        )
    }
}
