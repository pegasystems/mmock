package com.github.virelion.mmock.compiler.codegen

import com.github.virelion.mmock.compiler.codegen.utils.CodeBuilder

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
            appendln()
            imports.forEach {
                appendln("import $it")
            }
            appendln()
            lineOf("fun", typeParametersNames(), "MockInitializer.$originalName():", originalName + typeParametersNames(), "=", "$mockName(context)")
            appendln()
            appendln("class ${mockName}${typeParametersNamesWithVariance()}(override val mMockContext: MMockContext) : $originalName${typeParametersNames()}, ObjectMock { ")
            indent {
                builder.appendln("override val mocks: MockContainer = MockContainer(this)")
                appendln()

                properties.forEach {
                    it.generate(builder)
                }
                appendln()

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
