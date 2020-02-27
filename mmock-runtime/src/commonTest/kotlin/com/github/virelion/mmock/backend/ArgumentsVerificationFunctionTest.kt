package com.github.virelion.mmock.backend

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ArgumentsVerificationFunctionTest {
    @Test
    fun validArguments() {
        val arguments: Arguments = arrayOf(2, "", 3)
        val verificationFunction: Array<ArgumentsVerificationFunction> = arrayOf(
                { element: Any? -> element == 2 },
                { element: Any? -> element == "" },
                { element: Any? -> element == 3 }
        )

        assertTrue { arguments.verify(verificationFunction) }
    }

    @Test
    fun invalidArguments() {
        val arguments: Arguments = arrayOf(2, "", 3)
        val verificationFunction: Array<ArgumentsVerificationFunction> = arrayOf(
                { element: Any? -> element == 2 },
                { element: Any? -> element == 2 },
                { element: Any? -> element == 3 }
        )

        assertFalse { arguments.verify(verificationFunction) }
    }
}