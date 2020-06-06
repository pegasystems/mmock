package com.github.virelion.mmock.compiler.codegen.ir

import com.github.virelion.mmock.compiler.codegen.PropertyTemplate
import org.jetbrains.kotlin.ir.declarations.IrProperty

internal fun IrProperty.toCodeTemplate(): PropertyTemplate {
    return PropertyTemplate(
            name = this.name.asString(),
            type = this.getter!!.returnType.generateCode(),
            mutable = this.isVar
    )
}
