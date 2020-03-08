package com.github.virelion.mmock.scenarios

import com.github.virelion.mmock.NoMethodStubException
import com.github.virelion.mmock.dsl.*
import com.github.virelion.mmock.samples.ExampleInterface
import com.github.virelion.mmock.verifyFailed
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ArgumentMatcherScenarios {
    @Test
    fun `Equals matcher in mock`() = withMMock {
        val exampleInterface = mmock<ExampleInterface>()

        every { exampleInterface.function(eq(1)) } returns 1
        every { exampleInterface.function(eq(2)) } returns 2

        assertEquals(1, exampleInterface.function(1))
        assertEquals(2, exampleInterface.function(2))
        assertFailsWith<NoMethodStubException> { exampleInterface.function(3) }
    }

    @Test
    fun `Any matcher in mock`() = withMMock {
        val exampleInterface = mmock<ExampleInterface>()

        every { exampleInterface.function(any()) } returns 1

        assertEquals(1, exampleInterface.function(1))
        assertEquals(1, exampleInterface.function(2))
        assertEquals(1, exampleInterface.function(Int.MAX_VALUE))
    }

    @Test
    fun `InstanceOf matcher in mock`() = withMMock {
        val exampleInterface = mmock<ExampleInterface>()

        every { exampleInterface.functionAny(instanceOf<String>()) } returns "String"
        every { exampleInterface.functionAny(instanceOf<Int>()) } returns "Int"

        assertEquals("String", exampleInterface.functionAny(""))
        assertEquals("Int", exampleInterface.functionAny(1))
        assertFailsWith<NoMethodStubException> { exampleInterface.functionAny(3.0) }
    }

    @Test
    fun `Equals matcher in verification`() = withMMock {
        val exampleInterface = mmock<ExampleInterface>()

        every { exampleInterface.function(any()) } returns 1

        exampleInterface.function(1)
        exampleInterface.function(1)
        exampleInterface.function(2)
        exampleInterface.function(3)

        verify {
            exampleInterface.function(eq(1)) called twice
            exampleInterface.function(eq(2)) called once
            exampleInterface.function(eq(3)) called once
            exampleInterface.function(eq(4)) called never
        }

        verifyFailed { exampleInterface.function(eq(1)) called once }
        verifyFailed { exampleInterface.function(eq(2)) called twice }
        verifyFailed { exampleInterface.function(eq(2)) called twice }
        verifyFailed { exampleInterface.function(eq(4)) called once }
    }

    @Test
    fun `Any matcher in verification`() = withMMock {
        val exampleInterface = mmock<ExampleInterface>()

        every { exampleInterface.function(any()) } returns 1

        exampleInterface.function(1)
        exampleInterface.function(1)
        exampleInterface.function(2)
        exampleInterface.function(3)

        verify {
            exampleInterface.function(any()) called times(4)
        }

        verifyFailed { exampleInterface.function(any()) called times(3) }
        verifyFailed { exampleInterface.function(any()) called times(2) }
        verifyFailed { exampleInterface.function(any()) called times(1) }
        verifyFailed { exampleInterface.function(any()) called times(0) }
    }

    @Test
    fun `InstanceOf matcher in verification`() = withMMock {
        val exampleInterface = mmock<ExampleInterface>()

        every { exampleInterface.functionAny(any()) } returns Unit

        exampleInterface.functionAny(1)
        exampleInterface.functionAny(1)
        exampleInterface.functionAny(1)
        exampleInterface.functionAny("A")
        exampleInterface.functionAny("B")
        exampleInterface.functionAny(false)

        verify {
            exampleInterface.functionAny(instanceOf<Int>()) called times(3)
            exampleInterface.functionAny(instanceOf<String>()) called twice
            exampleInterface.functionAny(instanceOf<Boolean>()) called once
        }
        verifyFailed { exampleInterface.functionAny(instanceOf<Int>()) called times(4) }
        verifyFailed { exampleInterface.functionAny(instanceOf<String>()) called once }
        verifyFailed { exampleInterface.functionAny(instanceOf<Boolean>()) called twice }
    }

    @Test
    fun `Multiple matchers - complex mock scenario`() = withMMock {
        val exampleInterface = mmock<ExampleInterface>()

        every { exampleInterface.multipleArgs(eq(1), eq(2), eq(3)) } returns 1
        every { exampleInterface.multipleArgs(eq(1), eq(2), eq(4)) } returns 2
        every { exampleInterface.multipleArgs(eq(1), eq(3), eq(4)) } returns 3
        every { exampleInterface.multipleArgs(eq("STR"), eq(3), eq(4)) } returns 4

        assertEquals(1, exampleInterface.multipleArgs(1, 2, 3))
        assertEquals(2, exampleInterface.multipleArgs(1, 2, 4))
        assertEquals(3, exampleInterface.multipleArgs(1, 3, 4))
        assertEquals(4, exampleInterface.multipleArgs("STR", 3, 4))

        assertFailsWith<NoMethodStubException> { exampleInterface.multipleArgs(0, 0, 0) }
    }

    @Test
    fun `Multiple matchers - complex verification scenario`() = withMMock {
        val exampleInterface = mmock<ExampleInterface>()

        every { exampleInterface.multipleArgs(any(), any(), any()) } returns 1

        exampleInterface.multipleArgs(1, 2, 3)
        exampleInterface.multipleArgs("", 2, 3)
        exampleInterface.multipleArgs("", "", 3)
        exampleInterface.multipleArgs(1, "", 3)
        exampleInterface.multipleArgs(1, "", false)

        verify {
            exampleInterface.multipleArgs(any(), any(), any()) called times(5)
            exampleInterface.multipleArgs(instanceOf<Int>(), any(), any()) called times(3)
            exampleInterface.multipleArgs(instanceOf<String>(), any(), any()) called times(2)
            exampleInterface.multipleArgs(instanceOf<String>(), instanceOf<String>(), any()) called once
            exampleInterface.multipleArgs(instanceOf<String>(), instanceOf<String>(), instanceOf<String>()) called never
            exampleInterface.multipleArgs(any(), any(), eq(false)) called once
        }

        verifyFailed { exampleInterface.multipleArgs(any(), any(), any()) called once }
        verifyFailed { exampleInterface.multipleArgs(instanceOf<Int>(), any(), any()) called times(2) }
        verifyFailed { exampleInterface.multipleArgs(instanceOf<String>(), any(), any()) called times(3) }
        verifyFailed { exampleInterface.multipleArgs(instanceOf<String>(), instanceOf<String>(), any()) called never }
        verifyFailed { exampleInterface.multipleArgs(instanceOf<String>(), instanceOf<String>(), instanceOf<String>()) called once }
        verifyFailed { exampleInterface.multipleArgs(any(), any(), eq(false)) called never }
    }
}