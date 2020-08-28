import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    kotlin("kapt")
    `maven-publish`
    id("org.jlleitschuh.gradle.ktlint")
}

version = "1.3.72"

val artifactoryURL: String by project
val artifactoryUser: String by project
val artifactoryPassword: String by project
val snapshotRepository: String by project
val releaseRepository: String by project

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["kotlin"])
        }

        if (artifactoryURL.isNotEmpty()) {
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
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("compiler-embeddable"))
    compileOnly("com.google.auto.service:auto-service:1.0-rc6")
    kapt("com.google.auto.service:auto-service:1.0-rc6")

    testImplementation(kotlin("test-junit"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
