package com.github.virelion.mmock.backend

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ArgumentsVerificationFunctionTest {
    @Test
    @JsName("Valid_arguments")
    fun `Valid arguments`() {
        val args: Array<out Any?> = arrayOf(2, "", 3)
        val verificationFunction: ArgumentsConstraints = mutableListOf(
                { element: Any? -> element == 2 },
                { element: Any? -> element == "" },
                { element: Any? -> element == 3 }
        )

        assertTrue { verificationFunction.verify(args) }
    }

    @Test
    @JsName("Invalid_arguments")
    fun `Invalid arguments`() {
        val args: Array<out Any?> = arrayOf(2, "", 3)
        val verificationFunction: ArgumentsConstraints = mutableListOf(
                { element: Any? -> element == 2 },
                { element: Any? -> element == 2 },
                { element: Any? -> element == 3 }
        )

        assertFalse { verificationFunction.verify(args) }
    }
}
