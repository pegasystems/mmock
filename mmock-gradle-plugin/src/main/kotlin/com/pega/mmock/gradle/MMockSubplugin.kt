/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.gradle

import com.google.auto.service.AutoService
import org.gradle.api.provider.Provider
import org.jetbrains.kotlin.gradle.plugin.*

@AutoService(KotlinCompilerPluginSupportPlugin::class)
class MMockSubplugin : KotlinCompilerPluginSupportPlugin {
    override fun applyToCompilation(kotlinCompilation: KotlinCompilation<*>): Provider<List<SubpluginOption>> {
        val project = kotlinCompilation.target.project
        return project.provider { emptyList() }
    }

    override fun getCompilerPluginId(): String = "mmock-codegen"

    override fun getPluginArtifact(): SubpluginArtifact =
        SubpluginArtifact(
            groupId = "com.pega.mmock",
            artifactId = "mmock-compiler-plugin",
            version = "1.4.21"
        )

    override fun isApplicable(kotlinCompilation: KotlinCompilation<*>): Boolean {
        return true
    }

    override fun getPluginArtifactForNative(): SubpluginArtifact {
        return SubpluginArtifact(
            groupId = "com.pega.mmock",
            artifactId = "mmock-compiler-plugin-native",
            version = "1.4.21"
        )
    }
}
