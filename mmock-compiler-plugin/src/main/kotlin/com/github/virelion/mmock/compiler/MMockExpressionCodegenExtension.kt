package com.github.virelion.mmock.compiler

import com.github.virelion.mmock.compiler.codegen.jvm.toCodeTemplate
import com.github.virelion.mmock.compiler.codegen.utils.CodeBuilder
import org.jetbrains.kotlin.codegen.ImplementationBodyCodegen
import org.jetbrains.kotlin.codegen.extensions.ExpressionCodegenExtension
import java.io.File
import java.io.PrintWriter

class MMockExpressionCodegenExtension(private val codegenDir: String) : ExpressionCodegenExtension {
    override fun generateClassSyntheticParts(codegen: ImplementationBodyCodegen) {
        with(codegen.descriptor) {
            if (annotations.hasAnnotation(Annotations.GENERATE_MOCK)) {
                val mockClassCodeTemplate = this.toCodeTemplate()

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
                    it.println(mockClassCodeTemplate.generate(CodeBuilder()))
                }
            }
        }
    }

    internal fun String.getPathFromPackageName(): String {
        return replace('.', File.separatorChar)
    }
}