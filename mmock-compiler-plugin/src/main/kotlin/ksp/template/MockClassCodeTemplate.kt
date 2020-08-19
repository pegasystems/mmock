/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package ksp.template

import ksp.utils.CodeBuilder

internal data class MockClassCodeTemplate(
    val pkg: String,
    val constructor: ConstructorCodeTemplate?,
    val imports: MutableList<String>,
    val typeParameters: List<TypeParameterCodeTemplate>,
    val originalName: String,
    val methods: List<CodeTemplate>,
    val properties: List<PropertyCodeTemplate>
) : CodeTemplate {
    val mockName: String
        get() = "${originalName}_Mock"

    init {
        updateImports()
    }

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
            appendln("class ${mockName}${typeParametersNamesWithVariance()}(override val mMockContext: MMockContext) : $originalName${typeParametersNames()}${parentConstructorParameters()}, ObjectMock { ")
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

    fun parentConstructorParameters(): String {
        if (constructor == null)
            return ""

        val filteredParameters = constructor.parameters.filter { !it.hasDefaultValue }
        return if (filteredParameters.isNotEmpty()) {
            filteredParameters.joinToString(prefix = "(", separator = ", ", postfix = ")") { it.getDefaultInstanceFunction() }
        } else {
            "()"
        }
    }

    private fun updateImports() {
        val params = constructor?.parameters ?: return
        params.forEach {
            if (!(imports.contains(it.getDefaultInstanceImport()) || it.hasDefaultValue))
                imports.add(it.getDefaultInstanceImport())
        }
    }
}
