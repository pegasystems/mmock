package com.github.virelion.mmock.compiler.codegen

import com.github.virelion.mmock.compiler.codegen.utils.CodeBuilder
import kotlin.test.Test

class MethodCodeTemplateTest {
    @Test
    fun isMethodCorrectlyGenerated() {
        val methodSource = MethodCodeTemplate(
                "testMethod",
                "Int",
                typeParameters = listOf("T", "R"),
                parameters = listOf(
                        ParameterCodeTemplate("a", "Int"),
                        ParameterCodeTemplate("b", "Int")
                ),
                suspend = false
        )

        assertCodeEquals(
                """
                    override fun <T, R> testMethod(a: Int, b: Int): Int {
                        return mocks.invoke("testMethod(Int, Int)", a, b)
                    }
                """,
                methodSource.generate(CodeBuilder())
        )
    }
}