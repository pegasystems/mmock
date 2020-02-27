package com.github.virelion.mmock.backend

import kotlin.test.Test
import kotlin.test.assertEquals

class FunctionMockTest {
    @Test
    fun canBeInvoked() = runSuspend {
        val functionMock = FunctionMock {
            (it[0] as Int) * 2
        }

        assertEquals(2, functionMock(1))
        assertEquals(4, functionMock(2))
        assertEquals(6, functionMock(3))
    }
}