plugins {
    kotlin("multiplatform") version "1.3.72" apply false
    id("org.jlleitschuh.gradle.ktlint") version "9.2.1" apply false
}

allprojects {
    repositories {
        mavenCentral()
        jcenter()
    }
}
