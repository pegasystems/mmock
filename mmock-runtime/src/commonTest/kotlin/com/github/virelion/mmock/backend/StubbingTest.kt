package com.github.virelion.mmock.backend

import com.github.virelion.mmock.NoMethodStubException
import com.github.virelion.mmock.dsl.*
import com.github.virelion.mmock.samples.ExampleInterface
import com.github.virelion.mmock.verifyFailed
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class StubbingTest {
    @Test
    fun simpleStub() = withMMock {
        val exampleInterface: ExampleInterface = mmock()

        every {
            exampleInterface.function(eq(1))
            exampleInterface.noargsFunction()
        } returns 1
        every { exampleInterface.function(eq(2)) } returns 2
        every { exampleInterface.function(eq(3)) } returns 3

        assertEquals(1, exampleInterface.function(1))
        assertEquals(1, exampleInterface.noargsFunction())
        assertEquals(2, exampleInterface.function(2))
        assertEquals(3, exampleInterface.function(3))
        assertFailsWith<NoMethodStubException> { exampleInterface.function(4) }

        every { exampleInterface.function(any()) } returns 4
        assertEquals(4, exampleInterface.function(42))
    }

    @Test
    fun simpleVerification() = withMMock {
        val exampleInterface = mmock<ExampleInterface>()

        every { exampleInterface.functionAny(any()) } returns Unit

        exampleInterface.functionAny(1)
        exampleInterface.functionAny(2)
        exampleInterface.functionAny("String")

        verify {
            exampleInterface.functionAny(eq(1)) called once
            exampleInterface.functionAny(eq(2)) called once
            exampleInterface.functionAny(eq("String")) called once
            exampleInterface.functionAny(instanceOf<Int>()) called twice
            exampleInterface.functionAny(instanceOf<String>()) called once
            exampleInterface.noargsFunction() called never
        }

        verifyFailed { exampleInterface.functionAny(eq(1)) called twice }
        verifyFailed { exampleInterface.functionAny(eq(2)) called twice }
        verifyFailed { exampleInterface.functionAny(eq("String")) called twice }
        verifyFailed { exampleInterface.functionAny(instanceOf<Int>()) called once }
        verifyFailed { exampleInterface.functionAny(instanceOf<String>()) called twice }
        verifyFailed { exampleInterface.noargsFunction() called once }
    }



}