package com.github.virelion.mmock.backend

import com.github.virelion.mmock.NoMethodStubException
import com.github.virelion.mmock.dsl.any
import com.github.virelion.mmock.dsl.eq
import com.github.virelion.mmock.dsl.mmock
import com.github.virelion.mmock.dsl.withMMock
import com.github.virelion.mmock.samples.ExampleInterface
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class StubbingTest {
    @Test
    fun simpleStub() = withMMock {
        val exampleInterface: ExampleInterface = mmock()

        every { exampleInterface.function(eq(1)) } returns 1
        every { exampleInterface.function(eq(2)) } returns 2
        every { exampleInterface.function(eq(3)) } returns 3

        assertEquals(1, exampleInterface.function(1))
        assertEquals(2, exampleInterface.function(2))
        assertEquals(3, exampleInterface.function(3))
        assertFailsWith<NoMethodStubException> { exampleInterface.function(4) }

        every { exampleInterface.function(any()) } returns 4
        assertEquals(4, exampleInterface.function(42))
    }
}