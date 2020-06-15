/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.compiler.codegen.ir

import com.pega.mmock.compiler.codegen.CodeTemplate
import com.pega.mmock.compiler.codegen.MockClassCodeTemplate
import com.pega.mmock.compiler.codegen.TypeParameterCodeTemplate
import java.lang.IllegalStateException
import org.jetbrains.kotlin.ir.declarations.IrClass
import org.jetbrains.kotlin.ir.declarations.IrProperty
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
import org.jetbrains.kotlin.ir.util.getPackageFragment

internal fun IrClass.toCodeTemplate(): CodeTemplate {
    return MockClassCodeTemplate(
            pkg = this.getPackageFragment()?.fqName?.asString() ?: throw IllegalStateException("No package"),
            originalName = this.name.asString(),
            imports = listOf(
                    "com.pega.mmock.dsl.MMockContext",
                    "com.pega.mmock.dsl.MockInitializer",
                    "com.pega.mmock.backend.ObjectMock",
                    "com.pega.mmock.backend.MockContainer"
            ),
            typeParameters = this.typeParameters.map {
                TypeParameterCodeTemplate(it.name.asString(), it.variance.label)
            },
            methods = this.declarations.filterIsInstance<IrSimpleFunction>().map { it.toCodeTemplate() },
            properties = this.declarations.filterIsInstance<IrProperty>().map { it.toCodeTemplate() }
    )
}
