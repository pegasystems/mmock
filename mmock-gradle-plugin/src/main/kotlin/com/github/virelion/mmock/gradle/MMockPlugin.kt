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

            val nativeTargetNames = multiplatformExtension.targets.filter {
                it.platformType == KotlinPlatformType.native
            }.mapNotNull { it?.compilations?.getByName("main")?.compileKotlinTask?.name }

            if (nativeTargetNames.isNotEmpty()) {
                multiplatformExtension.targets
                        .fold(mutableListOf<KotlinCompilation<*>>()) { acc, next ->
                            acc += next.compilations
                            acc
                        }
                        .filter { it.compilationName.contains("test", ignoreCase = true) }
                        .forEach {
                            project.tasks.getByName(it.compileKotlinTask.name).dependsOn(nativeTargetNames)
                        }
            } else {
                project.logger.error("MMock cannot generate classes for projects with no native targets")
            }
        }
    }
}
