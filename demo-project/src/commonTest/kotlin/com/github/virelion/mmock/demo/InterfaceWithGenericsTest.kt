package com.github.virelion.mmock.demo

import com.github.virelion.mmock.dsl.any
import com.github.virelion.mmock.dsl.once
import com.github.virelion.mmock.dsl.twice
import com.github.virelion.mmock.withMMock
import kotlin.test.Test
import kotlin.test.assertEquals

class InterfaceWithGenericsTest {
    @Test
    fun function() = withMMock {
        val mock = mock.InterfaceWithGenerics<Int, Int>()
        every { mock.function(1) } returns 1
        every { mock.function(2) } returns 2

        assertEquals(1, mock.function(1))
        assertEquals(2, mock.function(2))

        verify {
            invocation { mock.function(1) } called once
            invocation { mock.function(2) } called once
            invocation { mock.function(any()) } called twice
        }
    }
}