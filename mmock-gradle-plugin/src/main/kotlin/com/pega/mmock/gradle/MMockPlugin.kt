/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.gradle

import com.google.devtools.ksp.gradle.KspExtension
import java.io.File
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType

class MMockPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.plugins.apply("com.google.devtools.ksp")
        val kspExtension = project.extensions.getByType(KspExtension::class.java)

        val mmockCodegenDir =
                listOf(project.buildDir.path, "generated", "mmock", "commonTest")
                        .joinToString(separator = File.separator)

        kspExtension.arg("mmockCodegenDir", mmockCodegenDir)

        project.afterEvaluate {
            val multiplatformExtension = project.extensions.getByType(KotlinMultiplatformExtension::class.java)

            val commonTest = multiplatformExtension.sourceSets.getByName("commonTest")

            project.logger.info("MMock codegen directory: $mmockCodegenDir")
            commonTest.kotlin.srcDir(mmockCodegenDir)

            val kspCodegenPlatformTarget =
                    multiplatformExtension.targets
                            .firstOrNull { it.platformType == KotlinPlatformType.jvm && it.publishable }
                            ?: multiplatformExtension.targets
                                    .firstOrNull { it.platformType == KotlinPlatformType.androidJvm && it.publishable }

            if (kspCodegenPlatformTarget != null) {
                val taskName = "kspKotlin" + kspCodegenPlatformTarget.name.capitalize()

                project.configurations.getByName("ksp").dependencies.add(
                        project.dependencies.create("com.pega.mmock:mmock-ksp-plugin:${Version.value}")
                )

                multiplatformExtension.targets
                        .fold(mutableListOf<KotlinCompilation<*>>()) { acc, next ->
                            acc += next.compilations
                            acc
                        }
                        .filter { it.compilationName.contains("test", ignoreCase = true) }
                        .forEach {
                            project.tasks.getByName(it.compileKotlinTask.name).dependsOn(taskName)
                        }
            } else {
                project.logger.error("MMock cannot generate classes for projects with no jvm or android targets")
            }
        }
    }
}
