package com.github.virelion.mmock.scenarios

import com.github.virelion.mmock.dsl.any
import com.github.virelion.mmock.samples.ExampleInterface
import com.github.virelion.mmock.withMMock
import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertFailsWith

class SuspendFunctionScenario {
    @Test
    @JsName("Mock_can_throw_exception")
    fun `Mock can throw exception`() = withMMock {
        val myMock = mock.ExampleInterface()
        every { myMock.suspendFunction(any()) } throws IllegalStateException()

        assertFailsWith<IllegalStateException> {
            myMock.suspendFunction(1)
        }
    }
}
