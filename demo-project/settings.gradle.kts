pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        google()
        jcenter()
    }
}

rootProject.name = "demo-project"

include(":sub-module")

enableFeaturePreview("GRADLE_METADATA")
