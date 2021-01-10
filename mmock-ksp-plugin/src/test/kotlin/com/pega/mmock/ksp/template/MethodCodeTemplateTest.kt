/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.ksp.template

import com.pega.mmock.ksp.utils.CodeBuilder
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
