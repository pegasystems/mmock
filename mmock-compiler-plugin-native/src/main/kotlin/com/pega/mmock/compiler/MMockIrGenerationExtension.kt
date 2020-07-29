/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.compiler

import com.pega.mmock.compiler.codegen.MockClassCodeTemplate
import com.pega.mmock.compiler.codegen.PackageStreamer
import com.pega.mmock.compiler.codegen.ir.checkConstraints
import com.pega.mmock.compiler.codegen.ir.toCodeTemplate
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
                            .hasAnnotation(FqName("com.pega.mmock.GenerateMock"))
                }
                .apply { forEach { it.checkConstraints() } }
                .map { irClass -> irClass.toCodeTemplate() }
                .filterIsInstance<MockClassCodeTemplate>()

        PackageStreamer(codegenDir).stream(mockClasses)
    }
}
