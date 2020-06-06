package com.github.virelion.mmock.compiler.codegen

import com.github.virelion.mmock.compiler.codegen.utils.CodeBuilder
import java.io.File
import java.io.PrintWriter

internal class PackageStreamer(
    private val codegenDir: String
) {
    fun stream(sequence: Iterable<MockClassCodeTemplate>) {
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
