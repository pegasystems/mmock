package com.github.virelion.mmock.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType

class MMockPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.afterEvaluate {
            val multiplatformExtension = project.extensions.getByType(KotlinMultiplatformExtension::class.java)
            val commonTest = multiplatformExtension.sourceSets.getByName("commonTest")
            val codegenTargetDirectory = CodegenDestination.getDirectoryAbsolutePath(project)

            project.logger.info("MMock codegen directory: $codegenTargetDirectory")
            commonTest.kotlin.srcDir(codegenTargetDirectory)

            val jvmMainClassesName = multiplatformExtension.targets.filter {
                it.platformType == KotlinPlatformType.jvm || it.platformType == KotlinPlatformType.androidJvm
            }.firstOrNull()?.compilations?.getByName("main")?.compileKotlinTask?.name

            if (jvmMainClassesName != null) {
                multiplatformExtension.targets
                        .fold(mutableListOf<KotlinCompilation<*>>()) { acc, next ->
                            acc += next.compilations
                            acc
                        }
                        .filter { it.compilationName.contains("test", ignoreCase = true) }
                        .forEach {
                            project.tasks.getByName(it.compileKotlinTask.name).dependsOn(jvmMainClassesName)
                        }
            } else {
                project.logger.error("MMock cannot generate classes for projects with no jvm/android platforms")
            }
        }
    }
}
