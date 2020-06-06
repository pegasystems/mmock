package com.github.virelion.mmock.gradle

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
    override fun apply(
        project: Project,
        kotlinCompile: AbstractCompile,
        javaCompile: AbstractCompile?,
        variantData: Any?,
        androidProjectHandler: Any?,
        kotlinCompilation: KotlinCompilation<KotlinCommonOptions>?
    ): List<SubpluginOption> {
        return listOf(
                SubpluginOption("codegenDir", CodegenDestination.getDirectoryAbsolutePath(project))
        )
    }

    override fun getCompilerPluginId(): String = "mmock-codegen"

    override fun getPluginArtifact(): SubpluginArtifact =
        SubpluginArtifact(
            groupId = "com.github.virelion.mmock",
            artifactId = "mmock-compiler-plugin",
            version = "1.3.72"
        )

    override fun getNativeCompilerPluginArtifact(): SubpluginArtifact? =
            SubpluginArtifact(
                    groupId = "com.github.virelion.mmock",
                    artifactId = "mmock-compiler-plugin-native",
                    version = "1.3.72"
            )

    override fun isApplicable(project: Project, task: AbstractCompile): Boolean = project.plugins.hasPlugin(MMockPlugin::class.java)
}
