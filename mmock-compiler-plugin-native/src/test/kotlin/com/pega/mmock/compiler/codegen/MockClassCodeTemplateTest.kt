/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.compiler.codegen

import com.pega.mmock.compiler.codegen.utils.CodeBuilder
import kotlin.test.Test

class MockClassCodeTemplateTest {
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

        val mockClassSource = MockClassCodeTemplate(
                pkg = "my.test",
                originalName = "TestClass",
                imports = arrayListOf("test.Test"),
                methods = listOf(methodSource),
                constructor = null,
                typeParameters = listOf(),
                properties = listOf(PropertyTemplate("property", "Int", true))
        )

        assertCodeEquals(
                """
                    package my.test

                    import test.Test

                    fun MockInitializer.TestClass(): TestClass = TestClass_Mock(context)

                    class TestClass_Mock(override val mMockContext: MMockContext) : TestClass, ObjectMock {
                        override val mocks: MockContainer = MockContainer(this)

                        override var property: Int
                            get() { return mocks.invoke("`property`(GET, property)") }
                            set(value) { return mocks.invoke("`property`(SET, property)", value) }

                        override fun <T, R> testMethod(a: Int, b: Int): Int {
                            return mocks.invoke("testMethod(Int, Int)", a, b)
                        }
                    }
                """,
                mockClassSource.generate(CodeBuilder())
        )
    }
}
