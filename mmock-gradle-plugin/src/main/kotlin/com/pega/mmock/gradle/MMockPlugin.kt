/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.gradle

import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType
import org.jetbrains.kotlin.gradle.tasks.KotlinNativeCompile
import org.jetbrains.kotlin.konan.target.Family

class MMockPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.afterEvaluate {
            val multiplatformExtension = project.extensions.getByType(KotlinMultiplatformExtension::class.java)
            val commonTest = multiplatformExtension.sourceSets.getByName("commonTest")
            val codegenTargetDirectory = CodegenDestination.getDirectoryAbsolutePath(project)

            project.logger.info("MMock codegen directory: $codegenTargetDirectory")
            commonTest.kotlin.srcDir(codegenTargetDirectory)

            val nativeTargetNames = multiplatformExtension.targets
                    .filter { it.platformType == KotlinPlatformType.native && it.publishable }
                    .filter {
                        // For some reason linux targets are publishable even in other OS
                        val compilation = it.compilations.getByName("main").compileKotlinTask as KotlinNativeCompile

                        return@filter compilation.compilation.konanTarget.family != Family.LINUX ||
                                (Os.isFamily(Os.FAMILY_UNIX) && !Os.isFamily(Os.FAMILY_MAC))
                    }
                    .mapNotNull { it?.compilations?.getByName("main")?.compileKotlinTask?.name }

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
