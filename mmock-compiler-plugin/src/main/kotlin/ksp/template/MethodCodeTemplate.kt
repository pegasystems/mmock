/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package ksp.template

import ksp.utils.CodeBuilder

internal class MethodCodeTemplate(
    val name: String,
    val returnType: String,
    val typeParameters: List<String>,
    val parameters: List<ParameterCodeTemplate>,
    val suspend: Boolean
) : CodeTemplate {
    override fun generate(builder: CodeBuilder): String {
        return builder.build {
            lineOf(
                    "override",
                    generateSuspend(),
                    "fun",
                    generateTypeParametersCode(),
                    "$name(${generateParametersDefinitionCode()}):",
                    returnType,
                    "{"
            )
            indent {
                appendln("return mocks.invoke${generateInvocationArgumentsString()}")
            }
            appendln("}")
        }
    }

    internal fun generateSuspend() = if (suspend) "suspend" else ""

    internal fun generateTypeParametersCode(): String {
        if (typeParameters.isEmpty()) return ""

        return typeParameters.joinToString(
                separator = ", ",
                prefix = "<",
                postfix = ">"
        )
    }

    internal fun generateParametersDefinitionCode(): String {
        return parameters.joinToString(
                separator = ", "
        ) {
            "${it.name}: ${it.type}"
        }
    }

    internal fun generateSafeName(): String {
        return "\"$name(${parameters.joinToString(separator = ", ") { it.type }})\""
    }

    internal fun generateInvocationArgumentsString(): String {
        val list: List<String> = listOf(generateSafeName()) + parameters.map { it.name }

        return list.joinToString(
                separator = ", ",
                prefix = "(",
                postfix = ")"
        )
    }
}
