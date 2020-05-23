package com.github.virelion.mmock.scenarios

import com.github.virelion.mmock.MMockStubbingException
import com.github.virelion.mmock.withMMock
import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertFailsWith

class RegularFunctionScenario {
    @Test
    @JsName("No_recordings_in_every_block_are_throwing_exception")
    fun `No recordings in every block are throwing exception`() = withMMock {
        assertFailsWith<MMockStubbingException> {
            every {} returns Unit
        }
    }
}
