/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.gradle

import org.gradle.api.Project
import org.jetbrains.kotlin.konan.file.File

object CodegenDestination {
    val S = File.separator
    val GENERATION_PATH = "${S}generated${S}mmock${S}commonTest"

    fun getDirectoryAbsolutePath(project: Project): String {
        return project.buildDir.path + GENERATION_PATH
    }
}
