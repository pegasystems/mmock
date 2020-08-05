/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.scenarios

import com.pega.mmock.MMockRecordingException
import com.pega.mmock.NoMethodStubException
import com.pega.mmock.dsl.any
import com.pega.mmock.dsl.eq
import com.pega.mmock.dsl.never
import com.pega.mmock.dsl.once
import com.pega.mmock.dsl.times
import com.pega.mmock.dsl.twice
import com.pega.mmock.samples.ExampleInterface
import com.pega.mmock.verifyFailed
import com.pega.mmock.withMMock
import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class SuspendArgumentMatcherScenarios {
    @Test
    @JsName("Equals_matcher_in_mock")
    fun `Equals matcher in mock`() = withMMock {
        val exampleInterface = mock.ExampleInterface()

        every { exampleInterface.suspendFunction(eq(1)) } returns 1
        every { exampleInterface.suspendFunction(eq(2)) } returns 2

        assertEquals(1, exampleInterface.suspendFunction(1))
        assertEquals(2, exampleInterface.suspendFunction(2))
        assertFailsWith<NoMethodStubException> { exampleInterface.suspendFunction(3) }
    }

    @Test
    @JsName("Any_matcher_in_mock")
    fun `Any matcher in mock`() = withMMock {
        val exampleInterface = mock.ExampleInterface()

        every { exampleInterface.suspendFunction(any()) } returns 1

        assertEquals(1, exampleInterface.suspendFunction(1))
        assertEquals(1, exampleInterface.suspendFunction(2))
        assertEquals(1, exampleInterface.suspendFunction(Int.MAX_VALUE))
    }

    @Test
    @JsName("InstanceOf_matcher_in_mock")
    fun `InstanceOf matcher in mock`() = withMMock {
        val exampleInterface = mock.ExampleInterface()

        every { exampleInterface.suspendFunctionAny(any<String>()) } returns "String"
        every { exampleInterface.suspendFunctionAny(any<Int>()) } returns "Int"

        assertEquals("String", exampleInterface.suspendFunctionAny(""))
        assertEquals("Int", exampleInterface.suspendFunctionAny(1))
        assertFailsWith<NoMethodStubException> { exampleInterface.suspendFunctionAny(false) }
    }

    @Test
    @JsName("Equals_matcher_in_verification")
    fun `Equals matcher in verification`() = withMMock {
        val exampleInterface = mock.ExampleInterface()

        every { exampleInterface.suspendFunction(any()) } returns 1

        exampleInterface.suspendFunction(1)
        exampleInterface.suspendFunction(1)
        exampleInterface.suspendFunction(2)
        exampleInterface.suspendFunction(3)

        verify {
            invocation { exampleInterface.suspendFunction(eq(1)) } called twice
            invocation { exampleInterface.suspendFunction(eq(2)) } called once
            invocation { exampleInterface.suspendFunction(eq(3)) } called once
            invocation { exampleInterface.suspendFunction(eq(4)) } called never
        }

        verifyFailed { invocation { exampleInterface.suspendFunction(eq(1)) } called once }
        verifyFailed { invocation { exampleInterface.suspendFunction(eq(2)) } called twice }
        verifyFailed { invocation { exampleInterface.suspendFunction(eq(2)) } called twice }
        verifyFailed { invocation { exampleInterface.suspendFunction(eq(4)) } called once }
    }

    @Test
    @JsName("Any_matcher_in_verification")
    fun `Any matcher in verification`() = withMMock {
        val exampleInterface = mock.ExampleInterface()

        every { exampleInterface.suspendFunctionAny(any()) } returns 1

        exampleInterface.suspendFunctionAny(1)
        exampleInterface.suspendFunctionAny(1)
        exampleInterface.suspendFunctionAny(2)
        exampleInterface.suspendFunctionAny(3)

        verify {
            invocation { exampleInterface.suspendFunctionAny(any()) } called times(4)
        }

        verifyFailed { invocation { exampleInterface.suspendFunctionAny(any()) } called times(3) }
        verifyFailed { invocation { exampleInterface.suspendFunctionAny(any()) } called times(2) }
        verifyFailed { invocation { exampleInterface.suspendFunctionAny(any()) } called times(1) }
        verifyFailed { invocation { exampleInterface.suspendFunctionAny(any()) } called times(0) }
    }

    @Test
    @JsName("InstanceOf_matcher_in_verification")
    fun `InstanceOf matcher in verification`() = withMMock {
        val exampleInterface = mock.ExampleInterface()

        every { exampleInterface.suspendFunctionAny(any()) } returns Unit

        exampleInterface.suspendFunctionAny(1)
        exampleInterface.suspendFunctionAny(1)
        exampleInterface.suspendFunctionAny(1)
        exampleInterface.suspendFunctionAny("A")
        exampleInterface.suspendFunctionAny("B")
        exampleInterface.suspendFunctionAny(false)

        verify {
            invocation { exampleInterface.suspendFunctionAny(any<Int>()) } called times(3)
            invocation { exampleInterface.suspendFunctionAny(any<String>()) } called twice
            invocation { exampleInterface.suspendFunctionAny(any<Boolean>()) } called once
        }
        verifyFailed { invocation { exampleInterface.suspendFunctionAny(any<Int>()) } called times(4) }
        verifyFailed { invocation { exampleInterface.suspendFunctionAny(any<String>()) } called once }
        verifyFailed { invocation { exampleInterface.suspendFunctionAny(any<Boolean>()) } called twice }
    }

    @Test
    @JsName("Multiple_argument_matchers___complex_mock_scenario")
    fun `Multiple argument matchers - complex mock scenario`() = withMMock {
        val exampleInterface = mock.ExampleInterface()

        every { exampleInterface.suspendMultipleArgs(eq(1), eq(2), eq(3)) } returns 1
        every { exampleInterface.suspendMultipleArgs(eq(1), eq(2), eq(4)) } returns 2
        every { exampleInterface.suspendMultipleArgs(eq(1), eq(3), eq(4)) } returns 3
        every { exampleInterface.suspendMultipleArgs(eq("STR"), eq(3), eq(4)) } returns 4

        assertEquals(1, exampleInterface.suspendMultipleArgs(1, 2, 3))
        assertEquals(2, exampleInterface.suspendMultipleArgs(1, 2, 4))
        assertEquals(3, exampleInterface.suspendMultipleArgs(1, 3, 4))
        assertEquals(4, exampleInterface.suspendMultipleArgs("STR", 3, 4))

        assertFailsWith<NoMethodStubException> { exampleInterface.suspendMultipleArgs(0, 0, 0) }
    }

    @Test
    @JsName("Multiple_argument_matchers___complex_verification_scenario")
    fun `Multiple argument matchers - complex verification scenario`() = withMMock {
        val exampleInterface = mock.ExampleInterface()

        every { exampleInterface.suspendMultipleArgs(any(), any(), any()) } returns 1

        exampleInterface.suspendMultipleArgs(1, 2, 3)
        exampleInterface.suspendMultipleArgs("", 2, 3)
        exampleInterface.suspendMultipleArgs("", "", 3)
        exampleInterface.suspendMultipleArgs(1, "", 3)
        exampleInterface.suspendMultipleArgs(1, "", false)

        verify {
            invocation { exampleInterface.suspendMultipleArgs(any(), any(), any()) } called times(5)
            invocation { exampleInterface.suspendMultipleArgs(any<Int>(), any(), any()) } called times(3)
            invocation { exampleInterface.suspendMultipleArgs(any<String>(), any(), any()) } called times(2)
            invocation { exampleInterface.suspendMultipleArgs(any<String>(), any<String>(), any()) } called once
            invocation { exampleInterface.suspendMultipleArgs(any<String>(), any<String>(), any<String>()) } called never
            invocation { exampleInterface.suspendMultipleArgs(any(), any(), eq(false)) } called once
        }

        verifyFailed { invocation { exampleInterface.suspendMultipleArgs(any(), any(), any()) } called once }
        verifyFailed { invocation { exampleInterface.suspendMultipleArgs(any<Int>(), any(), any()) } called times(2) }
        verifyFailed { invocation { exampleInterface.suspendMultipleArgs(any<String>(), any(), any()) } called times(3) }
        verifyFailed { invocation { exampleInterface.suspendMultipleArgs(any<String>(), any<String>(), any()) } called never }
        verifyFailed { invocation { exampleInterface.suspendMultipleArgs(any<String>(), any<String>(), any<String>()) } called once }
        verifyFailed { invocation { exampleInterface.suspendMultipleArgs(any(), any(), eq(false)) } called never }
    }

    @Test
    @JsName("Implicit_equals_argument_matcher_during_recording")
    fun `Implicit equals argument matcher during recording`() = withMMock {
        val exampleInterface = mock.ExampleInterface()

        every { exampleInterface.suspendMultipleArgs(1, 2, 3) } returns 1

        assertEquals(1, exampleInterface.suspendMultipleArgs(1, 2, 3))
        assertFailsWith<NoMethodStubException> { exampleInterface.suspendMultipleArgs(0, 0, 0) }

        verify {
            invocation { exampleInterface.suspendMultipleArgs(1, 2, 3) } called once
            invocation { exampleInterface.suspendMultipleArgs(3, 2, 1) } called never
        }

        verifyFailed { invocation { exampleInterface.suspendMultipleArgs(1, 2, 3) } called never }
        verifyFailed { invocation { exampleInterface.suspendMultipleArgs(3, 2, 1) } called once }
    }

    @Test
    @JsName("Error_is_thrown_in_case_of_mixed_matcher_usage")
    fun `Error is thrown in case of mixed matcher usage`() = withMMock {
        val exampleInterface = mock.ExampleInterface()

        assertFailsWith<MMockRecordingException> {
            every { exampleInterface.suspendMultipleArgs(1, any(), 3) } returns 1
        }

        assertFailsWith<MMockRecordingException> {
            verify {
                invocation { exampleInterface.suspendMultipleArgs(any(), 2, 3) } called once
            }
        }
    }
}
