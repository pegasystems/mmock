package com.github.virelion.mmock.scenarios

import com.github.virelion.mmock.dsl.twice
import com.github.virelion.mmock.samples.ExampleInterface
import com.github.virelion.mmock.verifyFailed
import com.github.virelion.mmock.withMMock
import kotlin.js.JsName
import kotlin.test.Test

class InvocationCountVerificationScenarios {
    @Test
    @JsName("Assumed_called_once_when_not_specified")
    fun `Assumed called once when not specified`() = withMMock {
        val mock = mock.ExampleInterface()
        every { mock.function(1) } returns 1

        mock.function(1)

        verify {
            invocation { mock.function(1) }
        }

        verifyFailed { invocation { mock.function(2) } }
        verifyFailed { invocation { mock.function(2) } called twice }
    }
}