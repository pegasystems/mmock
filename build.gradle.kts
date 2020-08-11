buildscript {
    repositories {
        jcenter()
        mavenCentral()
        google()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.5.0")
    }
}

plugins {
    kotlin("multiplatform") version "1.3.72" apply false
    id("org.jlleitschuh.gradle.ktlint") version "9.2.1" apply false
    id("com.gradle.plugin-publish") version "0.12.0" apply false
}

allprojects {
    repositories {
        mavenCentral()
        jcenter()
        google()
    }
}
