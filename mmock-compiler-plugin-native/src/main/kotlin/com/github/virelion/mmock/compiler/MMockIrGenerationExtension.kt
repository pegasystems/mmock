package com.github.virelion.mmock.compiler

import com.github.virelion.mmock.compiler.codegen.MockClassCodeTemplate
import com.github.virelion.mmock.compiler.codegen.PackageStreamer
import com.github.virelion.mmock.compiler.codegen.ir.toCodeTemplate
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.ir.declarations.IrClass
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.name.FqName

class MMockIrGenerationExtension(val codegenDir: String) : IrGenerationExtension {
    override fun generate(moduleFragment: IrModuleFragment, pluginContext: IrPluginContext) {
        val mockClasses = moduleFragment.files
                .flatMap { irFile -> irFile.declarations }
                .filterIsInstance<IrClass>()
                .filter { irClass ->
                    irClass.descriptor.annotations
                            .hasAnnotation(FqName("com.github.virelion.mmock.GenerateMock"))
                }
                .map { irClass -> irClass.toCodeTemplate() }
                .filterIsInstance<MockClassCodeTemplate>()

        PackageStreamer(codegenDir).stream(mockClasses)
    }
}
