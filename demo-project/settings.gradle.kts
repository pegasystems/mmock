pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
    }
}

rootProject.name = "demo-project"

include(":sub-module")

enableFeaturePreview("GRADLE_METADATA")
