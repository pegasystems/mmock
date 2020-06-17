/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.compiler.codegen

import com.pega.mmock.compiler.codegen.utils.CodeBuilder

internal data class MockClassCodeTemplate(
    val pkg: String,
    val imports: List<String>,
    val typeParameters: List<TypeParameterCodeTemplate>,
    val originalName: String,
    val methods: List<CodeTemplate>,
    val properties: List<PropertyTemplate>
) : CodeTemplate {
    val mockName: String
        get() = "${originalName}_Mock"

    override fun generate(builder: CodeBuilder): String {
        return builder.build {
            appendln("package $pkg")
            emptyLine()
            imports.sorted().forEach {
                appendln("import $it")
            }
            emptyLine()
            lineOf("fun", typeParametersNames(), "MockInitializer.$originalName():", originalName + typeParametersNames(), "=", "$mockName(context)")
            emptyLine()
            appendln("class ${mockName}${typeParametersNamesWithVariance()}(override val mMockContext: MMockContext) : $originalName${typeParametersNames()}, ObjectMock { ")
            indent {
                builder.appendln("override val mocks: MockContainer = MockContainer(this)")
                emptyLine()

                properties.forEach {
                    it.generate(builder)
                }
                emptyLine()

                methods.forEach {
                    it.generate(builder)
                }
            }
            appendln("}")
        }
    }

    fun typeParametersNames(): String {
        return if (typeParameters.isNotEmpty()) {
            typeParameters.joinToString(prefix = "<", postfix = ">", separator = ", ") { it.name }
        } else {
            ""
        }
    }

    fun typeParametersNamesWithVariance(): String {
        return if (typeParameters.isNotEmpty()) {
            typeParameters.joinToString(prefix = "<", postfix = ">", separator = ", ") { it.toString() }
        } else {
            ""
        }
    }
}
