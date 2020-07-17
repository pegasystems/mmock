/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.compiler.codegen.ir

import com.pega.mmock.compiler.codegen.ConstructorCodeTemplate
import com.pega.mmock.compiler.codegen.ConstructorParameterCodeTemplate
import org.jetbrains.kotlin.ir.declarations.IrConstructor
import org.jetbrains.kotlin.ir.util.hasDefaultValue

internal fun IrConstructor.toPrimaryCodeTemplate(): ConstructorCodeTemplate {
    return ConstructorCodeTemplate(
        this.valueParameters.map { ConstructorParameterCodeTemplate(it.name.toString(), it.type.generateCode(), it.hasDefaultValue()) }
    )
}


