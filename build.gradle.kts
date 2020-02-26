plugins {
    kotlin("multiplatform") version "1.3.61" apply false
}

allprojects {
    repositories {
        mavenCentral()
    }

    group = "com.github.virelion.mmock"
    version = "0.0.1-SNAPSHOT"
}
