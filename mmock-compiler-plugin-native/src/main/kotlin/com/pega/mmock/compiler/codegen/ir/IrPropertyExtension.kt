/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.compiler.codegen.ir

import com.pega.mmock.compiler.codegen.PropertyTemplate
import org.jetbrains.kotlin.ir.declarations.IrProperty

internal fun IrProperty.toCodeTemplate(): PropertyTemplate {
    return PropertyTemplate(
            name = this.name.asString(),
            type = this.getter!!.returnType.generateCode(),
            mutable = this.isVar
    )
}
