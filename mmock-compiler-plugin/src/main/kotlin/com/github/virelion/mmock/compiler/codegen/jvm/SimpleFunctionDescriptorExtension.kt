package com.github.virelion.mmock.compiler.codegen.jvm

import com.github.virelion.mmock.compiler.codegen.CodeTemplate
import com.github.virelion.mmock.compiler.codegen.MethodCodeTemplate
import com.github.virelion.mmock.compiler.codegen.ParameterCodeTemplate
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor

internal fun SimpleFunctionDescriptor.toCodeTemplate(): CodeTemplate {
    return MethodCodeTemplate(
            name = name.asString(),
            returnType = returnType.generateCode(),
            parameters = valueParameters.map { parameter ->
                ParameterCodeTemplate(
                        type = parameter.type.generateCode(),
                        name = parameter.name.asString()
                )
            },
            typeParameters = typeParameters.map { type ->
                type.name.asString()
            },
            suspend = isSuspend
    )
}