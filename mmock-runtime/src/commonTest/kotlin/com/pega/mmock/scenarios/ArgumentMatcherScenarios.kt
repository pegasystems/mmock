/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.scenarios

import com.pega.mmock.MMockRecordingException
import com.pega.mmock.NoMethodStubException
import com.pega.mmock.dsl.any
import com.pega.mmock.dsl.eq
import com.pega.mmock.dsl.instanceOf
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
import kotlin.test.assertNull

class ArgumentMatcherScenarios {
    @Test
    @JsName("Equals_matcher_in_mock")
    fun `Equals matcher in mock`() = withMMock {
        val exampleInterface = mock.ExampleInterface()

        every { exampleInterface.function(eq(1)) } returns 1
        every { exampleInterface.function(eq(2)) } returns 2

        assertEquals(1, exampleInterface.function(1))
        assertEquals(2, exampleInterface.function(2))
        assertFailsWith<NoMethodStubException> { exampleInterface.function(3) }
    }

    @Test
    @JsName("Any_matcher_in_mock")
    fun `Any matcher in mock`() = withMMock {
        val exampleInterface = mock.ExampleInterface()

        every { exampleInterface.function(any()) } returns 1

        assertEquals(1, exampleInterface.function(1))
        assertEquals(1, exampleInterface.function(2))
        assertEquals(1, exampleInterface.function(Int.MAX_VALUE))
    }

    @Test
    @JsName("InstanceOf_matcher_in_mock")
    fun `InstanceOf matcher in mock`() = withMMock {
        val exampleInterface = mock.ExampleInterface()

        every { exampleInterface.functionAny(instanceOf<String>()) } returns "String"
        every { exampleInterface.functionAny(instanceOf<Int>()) } returns "Int"

        assertEquals("String", exampleInterface.functionAny(""))
        assertEquals("Int", exampleInterface.functionAny(1))
        assertFailsWith<NoMethodStubException> { exampleInterface.functionAny(false) }
    }

    @Test
    @JsName("Equals_matcher_in_verification")
    fun `Equals matcher in verification`() = withMMock {
        val exampleInterface = mock.ExampleInterface()

        every { exampleInterface.function(any()) } returns 1

        exampleInterface.function(1)
        exampleInterface.function(1)
        exampleInterface.function(2)
        exampleInterface.function(3)

        verify {
            invocation { exampleInterface.function(eq(1)) } called twice
            invocation { exampleInterface.function(eq(2)) } called once
            invocation { exampleInterface.function(eq(3)) } called once
            invocation { exampleInterface.function(eq(4)) } called never
        }

        verifyFailed { invocation { exampleInterface.function(eq(1)) } called once }
        verifyFailed { invocation { exampleInterface.function(eq(2)) } called twice }
        verifyFailed { invocation { exampleInterface.function(eq(2)) } called twice }
        verifyFailed { invocation { exampleInterface.function(eq(4)) } called once }
    }

    @Test
    @JsName("Any_matcher_in_verification")
    fun `Any matcher in verification`() = withMMock {
        val exampleInterface = mock.ExampleInterface()

        every { exampleInterface.function(any()) } returns 1

        exampleInterface.function(1)
        exampleInterface.function(1)
        exampleInterface.function(2)
        exampleInterface.function(3)

        verify {
            invocation { exampleInterface.function(any()) } called times(4)
        }

        verifyFailed { invocation { exampleInterface.function(any()) } called times(3) }
        verifyFailed { invocation { exampleInterface.function(any()) } called times(2) }
        verifyFailed { invocation { exampleInterface.function(any()) } called times(1) }
        verifyFailed { invocation { exampleInterface.function(any()) } called times(0) }
    }

    @Test
    @JsName("InstanceOf_matcher_in_verification")
    fun `InstanceOf matcher in verification`() = withMMock {
        val exampleInterface = mock.ExampleInterface()

        every { exampleInterface.functionAny(any()) } returns Unit

        exampleInterface.functionAny(1)
        exampleInterface.functionAny(1)
        exampleInterface.functionAny(1)
        exampleInterface.functionAny("A")
        exampleInterface.functionAny("B")
        exampleInterface.functionAny(false)

        verify {
            invocation { exampleInterface.functionAny(instanceOf<Int>()) } called times(3)
            invocation { exampleInterface.functionAny(instanceOf<String>()) } called twice
            invocation { exampleInterface.functionAny(instanceOf<Boolean>()) } called once
        }
        verifyFailed { invocation { exampleInterface.functionAny(instanceOf<Int>()) } called times(4) }
        verifyFailed { invocation { exampleInterface.functionAny(instanceOf<String>()) } called once }
        verifyFailed { invocation { exampleInterface.functionAny(instanceOf<Boolean>()) } called twice }
    }

    @Test
    @JsName("Multiple_argument_matchers___complex_mock_scenario")
    fun `Multiple argument matchers - complex mock scenario`() = withMMock {
        val exampleInterface = mock.ExampleInterface()

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
    @JsName("Multiple_argument_matchers___complex_verification_scenario")
    fun `Multiple argument matchers - complex verification scenario`() = withMMock {
        val exampleInterface = mock.ExampleInterface()

        every { exampleInterface.multipleArgs(any(), any(), any()) } returns 1

        exampleInterface.multipleArgs(1, 2, 3)
        exampleInterface.multipleArgs("", 2, 3)
        exampleInterface.multipleArgs("", "", 3)
        exampleInterface.multipleArgs(1, "", 3)
        exampleInterface.multipleArgs(1, "", false)

        verify {
            invocation { exampleInterface.multipleArgs(any(), any(), any()) } called times(5)
            invocation { exampleInterface.multipleArgs(instanceOf<Int>(), any(), any()) } called times(3)
            invocation { exampleInterface.multipleArgs(instanceOf<String>(), any(), any()) } called times(2)
            invocation { exampleInterface.multipleArgs(instanceOf<String>(), instanceOf<String>(), any()) } called once
            invocation { exampleInterface.multipleArgs(instanceOf<String>(), instanceOf<String>(), instanceOf<String>()) } called never
            invocation { exampleInterface.multipleArgs(any(), any(), eq(false)) } called once
        }

        verifyFailed { invocation { exampleInterface.multipleArgs(any(), any(), any()) } called once }
        verifyFailed { invocation { exampleInterface.multipleArgs(instanceOf<Int>(), any(), any()) } called times(2) }
        verifyFailed { invocation { exampleInterface.multipleArgs(instanceOf<String>(), any(), any()) } called times(3) }
        verifyFailed { invocation { exampleInterface.multipleArgs(instanceOf<String>(), instanceOf<String>(), any()) } called never }
        verifyFailed { invocation { exampleInterface.multipleArgs(instanceOf<String>(), instanceOf<String>(), instanceOf<String>()) } called once }
        verifyFailed { invocation { exampleInterface.multipleArgs(any(), any(), eq(false)) } called never }
    }

    @Test
    @JsName("Implicit_equals_argument_matcher_during_recording")
    fun `Implicit equals argument matcher during recording`() = withMMock {
        val exampleInterface = mock.ExampleInterface()

        every { exampleInterface.multipleArgs(1, 2, 3) } returns 1

        assertEquals(1, exampleInterface.multipleArgs(1, 2, 3))
        assertFailsWith<NoMethodStubException> { exampleInterface.multipleArgs(0, 0, 0) }

        verify {
            invocation { exampleInterface.multipleArgs(1, 2, 3) } called once
            invocation { exampleInterface.multipleArgs(3, 2, 1) } called never
        }

        verifyFailed { invocation { exampleInterface.multipleArgs(1, 2, 3) } called never }
        verifyFailed { invocation { exampleInterface.multipleArgs(3, 2, 1) } called once }
    }

    @Test
    @JsName("Error_is_thrown_in_case_of_mixed_matcher_usage")
    fun `Error is thrown in case of mixed matcher usage`() = withMMock {
        val exampleInterface = mock.ExampleInterface()

        assertFailsWith<MMockRecordingException> {
            every { exampleInterface.multipleArgs(1, any(), 3) } returns 1
        }

        assertFailsWith<MMockRecordingException> {
            verify {
                invocation { exampleInterface.multipleArgs(any(), 2, 3) } called once
            }
        }
    }

    @Test
    @JsName("Mocked_null_values")
    fun `Mocked null values`() = withMMock {
        val myMock = mock.ExampleInterface()
        every { myMock.mutableProperty } returns null
        assertNull(myMock.mutableProperty)
    }

    @Test
    @JsName("Test_mutable_property")
    fun `Test mutable property`() = withMMock {
        val myMock: ExampleInterface = mock.ExampleInterface()
        every { myMock.mutableProperty = any() } returns Unit
        every { myMock.mutableProperty } returns 1

        assertEquals(1, myMock.mutableProperty)
        myMock.mutableProperty = null
        assertEquals(1, myMock.mutableProperty)

        verify {
            invocation { myMock.mutableProperty } called twice
            invocation { myMock.mutableProperty = any() } called once
        }
    }

    @Test
    @JsName("Test_immutable_property")
    fun `Test immutable property`() = withMMock {
        val myMock: ExampleInterface = mock.ExampleInterface()
        every { myMock.property } returns 1
        assertEquals(1, myMock.property)

        verify {
            invocation { myMock.property } called once
        }
    }
}
