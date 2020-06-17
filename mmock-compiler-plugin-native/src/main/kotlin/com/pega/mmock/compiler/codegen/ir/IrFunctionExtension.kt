/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.compiler.codegen.ir

import com.pega.mmock.compiler.codegen.CodeTemplate
import com.pega.mmock.compiler.codegen.MethodCodeTemplate
import com.pega.mmock.compiler.codegen.ParameterCodeTemplate
import org.jetbrains.kotlin.ir.declarations.IrFunction
import org.jetbrains.kotlin.ir.util.isSuspend

internal fun IrFunction.toCodeTemplate(): CodeTemplate {
    return MethodCodeTemplate(
            name = this.name.asString(),
            returnType = this.returnType.generateCode(),
            typeParameters = this.typeParameters.map { it.name.asString() },
            parameters = this.valueParameters.map { ParameterCodeTemplate(it.name.asString(), it.type.generateCode()) },
            suspend = this.isSuspend
    )
}
