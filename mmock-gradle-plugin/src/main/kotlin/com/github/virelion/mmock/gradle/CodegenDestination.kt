package com.github.virelion.mmock.gradle

import org.gradle.api.Project
import org.jetbrains.kotlin.konan.file.File


object CodegenDestination {
    val S = File.separator
    val GENERATION_PATH = "${S}generated${S}mmock${S}commonTest"

    fun getDirectoryAbsolutePath(project: Project): String {
        return project.buildDir.path + GENERATION_PATH
    }
}