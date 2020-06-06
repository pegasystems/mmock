package com.github.virelion.mmock.compiler.codegen.ir

import com.github.virelion.mmock.compiler.codegen.CodeTemplate
import com.github.virelion.mmock.compiler.codegen.MethodCodeTemplate
import com.github.virelion.mmock.compiler.codegen.ParameterCodeTemplate
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
