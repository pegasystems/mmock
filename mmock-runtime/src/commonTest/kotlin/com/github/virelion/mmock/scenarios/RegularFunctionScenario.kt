package com.github.virelion.mmock.scenarios

import com.github.virelion.mmock.MMockStubbingException
import com.github.virelion.mmock.dsl.*
import com.github.virelion.mmock.samples.ExampleInterface
import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertFailsWith

class RegularFunctionScenario {
    @Test
    @JsName("Multiple_recordings_in_every_block_are_throwing_exception")
    fun `Multiple recordings in every block are throwing exception`() = withMMock {
        val exampleInterface: ExampleInterface = mmock()
        assertFailsWith<MMockStubbingException> {
            every {
                exampleInterface.function(eq(1))
                exampleInterface.function(eq(1))
            } returns 1
        }
    }

    @Test
    @JsName("No_recordings_in_every_block_are_throwing_exception")
    fun `No recordings in every block are throwing exception`() = withMMock {
        assertFailsWith<MMockStubbingException> {
            every {} returns Unit
        }
    }
}