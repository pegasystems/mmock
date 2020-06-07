package com.github.virelion.mmock.scenarios

import com.github.virelion.mmock.MMockStubbingException
import com.github.virelion.mmock.dsl.any
import com.github.virelion.mmock.samples.ExampleInterface
import com.github.virelion.mmock.withMMock
import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class RegularFunctionScenario {
    @Test
    @JsName("No_recordings_in_every_block_are_throwing_exception")
    fun `No recordings in every block are throwing exception`() = withMMock {
        assertFailsWith<MMockStubbingException> {
            every {} returns Unit
        }
    }

    @Test
    @JsName("Verification_recording_outside_of_invocation")
    fun `Verification recording outside of invocation`() = withMMock {
        val myMock = mock.ExampleInterface()
        every { myMock.suspendFunction(any()) } returns 1

        myMock.suspendFunction(1)

        try {
            verify {
                myMock.suspendFunction(any())
            }
        } catch (e: IllegalStateException) {
            assertEquals("Mock invoked outside of `invocation { }`", e.message)
        }
    }

    @Test
    @JsName("Mock_can_throw_exception")
    fun `Mock can throw exception`() = withMMock {
        val myMock = mock.ExampleInterface()
        every { myMock.function(any()) } throws IllegalStateException()

        assertFailsWith<IllegalStateException> {
            myMock.function(1)
        }
    }
}
