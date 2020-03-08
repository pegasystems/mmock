package com.github.virelion.mmock.backend

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals

class FunctionMockTest {
    @Test
    @JsName("Can_be_invoked")
    fun `Can be invoked`() {
        val functionMock = FunctionMock(mutableListOf({ _: Any? -> true })) {
            (it[0] as Int) * 2
        }

        assertEquals(2, functionMock(1))
        assertEquals(4, functionMock(2))
        assertEquals(6, functionMock(3))
    }
}