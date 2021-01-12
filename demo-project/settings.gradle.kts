pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        google()
        jcenter()
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
    }
}

rootProject.name = "demo-project"

include(":sub-module")

enableFeaturePreview("GRADLE_METADATA")
