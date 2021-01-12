package com.pega.mmock.ksp

import com.pega.mmock.ksp.template.MockClassCodeTemplate
import com.pega.mmock.ksp.utils.CodeBuilder
import java.io.File
import java.io.PrintWriter

internal class PackageStreamer(
    private val codegenDir: String
) {
    fun stream(sequence: Sequence<MockClassCodeTemplate>) {
        sequence.forEach { mockClassCodeTemplate ->
            val packageDir = codegenDir +
                    File.separator +
                    mockClassCodeTemplate.pkg.getPathFromPackageName()
            File(packageDir).mkdirs()

            val output = File(
                    packageDir +
                            File.separator +
                            mockClassCodeTemplate.mockName + ".kt"
            )
            output.createNewFile()

            val printWriter = PrintWriter(output)
            printWriter.use {
                it.print(mockClassCodeTemplate.generate(CodeBuilder()))
            }
        }
    }

    internal fun String.getPathFromPackageName(): String {
        return replace('.', File.separatorChar)
    }
}
