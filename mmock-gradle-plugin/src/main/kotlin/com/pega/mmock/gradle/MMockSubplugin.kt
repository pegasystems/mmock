/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.gradle

import com.google.auto.service.AutoService
import org.gradle.api.Project
import org.gradle.api.tasks.compile.AbstractCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinCommonOptions
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinGradleSubplugin
import org.jetbrains.kotlin.gradle.plugin.SubpluginArtifact
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption

@AutoService(KotlinGradleSubplugin::class)
class MMockSubplugin : KotlinGradleSubplugin<AbstractCompile> {
    override fun getCompilerPluginId(): String = "mmock-codegen"

    override fun getPluginArtifact(): SubpluginArtifact =
            SubpluginArtifact(
                    groupId = "com.pega.mmock",
                    artifactId = "mmock-compiler-plugin",
                    version = Version.value
            )

    override fun getNativeCompilerPluginArtifact(): SubpluginArtifact? =
            SubpluginArtifact(
                    groupId = "com.pega.mmock",
                    artifactId = "mmock-compiler-plugin-native",
                    version = Version.value
            )

    override fun isApplicable(project: Project, task: AbstractCompile): Boolean =
            project.plugins.hasPlugin(MMockPlugin::class.java)

    override fun apply(
        project: Project,
        kotlinCompile: AbstractCompile,
        javaCompile: AbstractCompile?,
        variantData: Any?,
        androidProjectHandler: Any?,
        kotlinCompilation: KotlinCompilation<KotlinCommonOptions>?
    ): List<SubpluginOption> {
        return emptyList()
    }
}
