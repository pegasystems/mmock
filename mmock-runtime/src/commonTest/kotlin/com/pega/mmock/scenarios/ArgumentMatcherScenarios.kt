/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.scenarios

import com.pega.mmock.MMockRecordingException
import com.pega.mmock.NoMethodStubException
import com.pega.mmock.dsl.any
import com.pega.mmock.dsl.anyArray
import com.pega.mmock.dsl.anyList
import com.pega.mmock.dsl.anyMap
import com.pega.mmock.dsl.anySet
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
import kotlin.test.assertNull

class ArgumentMatcherScenarios {
    class DelegatedList<T>(private val delegate: List<T> = listOf()) : List<T> by delegate
    class DelegatedMutableList<T>(private val delegate: MutableList<T> = mutableListOf()) : MutableList<T> by delegate
    class NewDelegatedMutableList<T>(private val delegate: MutableList<T> = mutableListOf()) : MutableList<T> by delegate

    class DelegatedSet<T>(private val delegate: Set<T> = setOf()) : Set<T> by delegate
    class DelegatedMutableSet<T>(private val delegate: MutableSet<T> = mutableSetOf()) : MutableSet<T> by delegate
    class NewDelegatedMutableSet<T>(private val delegate: MutableSet<T> = mutableSetOf()) : MutableSet<T> by delegate

    class DelegatedMap<T, E>(private val delegate: Map<T, E> = mapOf()) : Map<T, E> by delegate
    class DelegatedMutableMap<T, E>(private val delegate: MutableMap<T, E> = mutableMapOf()) : MutableMap<T, E> by delegate
    class NewDelegatedMutableMap<T, E>(private val delegate: MutableMap<T, E> = mutableMapOf()) : MutableMap<T, E> by delegate

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
    @JsName("Any_array_matcher_in_mock")
    fun `Any array matcher in mock`() = withMMock {
        val exampleInterface = mock.ExampleInterface()

        every { exampleInterface.functionArray(anyArray()) } returns 1

        assertEquals(1, exampleInterface.functionArray(emptyArray()))
        assertEquals(1, exampleInterface.functionArray(arrayOf(1, 2, 3, 4)))
    }

    @Test
    @JsName("Any_list_matcher_in_mock")
    fun `Any list matcher in mock`() = withMMock {
        val exampleInterface = mock.ExampleInterface()

        every { exampleInterface.functionList(anyList<MutableList<Int>, Int>()) } returns 2
        every { exampleInterface.functionList(anyList()) } returns 1

        assertEquals(1, exampleInterface.functionList(DelegatedList(listOf(1, 2, 3))))
        assertEquals(2, exampleInterface.functionList(DelegatedMutableList(mutableListOf(1, 2, 3))))
    }

    @Test
    @JsName("Any_set_matcher_in_mock")
    fun `Any set matcher in mock`() = withMMock {
        val exampleInterface = mock.ExampleInterface()

        every { exampleInterface.functionSet(anySet<MutableSet<Int>, Int>()) } returns 1
        every { exampleInterface.functionSet(anySet()) } returns 2

        assertEquals(2, exampleInterface.functionSet(DelegatedSet(setOf(1, 2, 3))))
        assertEquals(1, exampleInterface.functionSet(DelegatedMutableSet(mutableSetOf(1, 2, 3, 4))))
    }

    @Test
    @JsName("Any_map_matcher_in_mock")
    fun `Any map matcher in mock`() = withMMock {
        val exampleInterface = mock.ExampleInterface()

        every { exampleInterface.functionMap(anyMap<MutableMap<Int, Int>, Int, Int>()) } returns 1
        every { exampleInterface.functionMap(anyMap()) } returns 2

        assertEquals(2, exampleInterface.functionMap(DelegatedMap(mapOf(Pair(1, 2), Pair(3, 4)))))
        assertEquals(1, exampleInterface.functionMap(DelegatedMutableMap(mutableMapOf(Pair(1, 2), Pair(4, 5)))))
    }

    @Test
    @JsName("Any_collection_matchers_with_that_has_Any_type_as_parameter")
    fun `Any collection matchers with function that has Any type as parameter`() = withMMock {
        val exampleInterface = mock.ExampleInterface()

        every { exampleInterface.functionAny(anyList<MutableList<Int>, Int>()) } returns "MutableList"
        every { exampleInterface.functionAny(anySet<MutableSet<Int>, Int>()) } returns "MutableSet"
        every { exampleInterface.functionAny(anyMap<MutableMap<Int, Int>, Int, Int>()) } returns "MutableMap"
        every { exampleInterface.functionAny(anyList<List<Int>, Int>()) } returns "List"
        every { exampleInterface.functionAny(anySet<Set<Int>, Int>()) } returns "Set"
        every { exampleInterface.functionAny(anyMap<Map<Int, Int>, Int, Int>()) } returns "Map"
        every { exampleInterface.functionAny(anyArray<Int>()) } returns "Array"

        assertEquals("Array", exampleInterface.functionAny(arrayOf<Int>()))
        assertEquals("List", exampleInterface.functionAny(listOf<Int>()))
        assertEquals("Set", exampleInterface.functionAny(setOf<Int>()))
        assertEquals("Map", exampleInterface.functionAny(mapOf<Int, Int>()))
        assertEquals("MutableList", exampleInterface.functionAny(DelegatedMutableList<Int>(mutableListOf())))
        assertEquals("MutableSet", exampleInterface.functionAny(DelegatedMutableSet<Int>(mutableSetOf())))
        assertEquals("MutableMap", exampleInterface.functionAny(DelegatedMutableMap<Int, Int>(mutableMapOf())))
    }

    @Test
    @JsName("InstanceOf_matcher_in_mock")
    fun `InstanceOf matcher in mock`() = withMMock {
        val exampleInterface = mock.ExampleInterface()

        every { exampleInterface.functionAny(any<String>()) } returns "String"
        every { exampleInterface.functionAny(any<Int>()) } returns "Int"

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
    @JsName("Any_list_matcher_in_verification")
    fun `Any list matcher in verification`() = withMMock {
        val exampleInterface = mock.ExampleInterface()

        every { exampleInterface.functionList(anyList<MutableList<Int>, Int>()) } returns 2
        every { exampleInterface.functionList(anyList()) } returns 3

        exampleInterface.functionList(DelegatedList(listOf(1, 2, 3)))
        exampleInterface.functionList(DelegatedMutableList(mutableListOf(1, 2, 3)))
        exampleInterface.functionList(DelegatedList(listOf(7, 8, 9)))

        verify {
            invocation { exampleInterface.functionList(anyList<MutableList<Int>, Int>()) } called once
            invocation { exampleInterface.functionList(anyList()) } called times(3)
        }

        verifyFailed { invocation { exampleInterface.functionList(anyList()) } called twice }
        verifyFailed { invocation { exampleInterface.functionList(anyList()) } called once }
        verifyFailed { invocation { exampleInterface.functionList(anyList<MutableList<Int>, Int>()) } called twice }
    }

    @Test
    @JsName("Any_set_matcher_in_verification")
    fun `Any set matcher in verification`() = withMMock {
        val exampleInterface = mock.ExampleInterface()

        every { exampleInterface.functionSet(anySet<MutableSet<Int>, Int>()) } returns 2
        every { exampleInterface.functionSet(anySet()) } returns 3

        exampleInterface.functionSet(DelegatedSet(setOf(1, 2, 3)))
        exampleInterface.functionSet(DelegatedMutableSet(mutableSetOf(1, 2, 3)))
        exampleInterface.functionSet(DelegatedSet(setOf(7, 8, 9)))

        verify {
            invocation { exampleInterface.functionSet(anySet<MutableSet<Int>, Int>()) } called once
            invocation { exampleInterface.functionSet(anySet()) } called times(3)
        }

        verifyFailed { invocation { exampleInterface.functionSet(anySet()) } called twice }
        verifyFailed { invocation { exampleInterface.functionSet(anySet()) } called once }
        verifyFailed { invocation { exampleInterface.functionSet(anySet<MutableSet<Int>, Int>()) } called twice }
    }

    @Test
    @JsName("Any_map_matcher_in_verification")
    fun `Any map matcher in verification`() = withMMock {
        val exampleInterface = mock.ExampleInterface()

        every { exampleInterface.functionMap(anyMap<MutableMap<Int, Int>, Int, Int>()) } returns 2
        every { exampleInterface.functionMap(anyMap()) } returns 3

        exampleInterface.functionMap(DelegatedMap(mapOf(Pair(1, 2), Pair(3, 4))))
        exampleInterface.functionMap(DelegatedMutableMap(mutableMapOf(Pair(1, 2), Pair(3, 4))))
        exampleInterface.functionMap(DelegatedMap(mapOf(Pair(7, 8), Pair(9, 10))))

        verify {
            invocation { exampleInterface.functionMap(anyMap<MutableMap<Int, Int>, Int, Int>()) } called once
            invocation { exampleInterface.functionMap(anyMap()) } called times(3)
        }

        verifyFailed { invocation { exampleInterface.functionMap(anyMap()) } called twice }
        verifyFailed { invocation { exampleInterface.functionMap(anyMap()) } called once }
        verifyFailed { invocation { exampleInterface.functionMap(anyMap<MutableMap<Int, Int>, Int, Int>()) } called twice }
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
            invocation { exampleInterface.functionAny(any<Int>()) } called times(3)
            invocation { exampleInterface.functionAny(any<String>()) } called twice
            invocation { exampleInterface.functionAny(any<Boolean>()) } called once
        }
        verifyFailed { invocation { exampleInterface.functionAny(any<Int>()) } called times(4) }
        verifyFailed { invocation { exampleInterface.functionAny(any<String>()) } called once }
        verifyFailed { invocation { exampleInterface.functionAny(any<Boolean>()) } called twice }
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
            invocation { exampleInterface.multipleArgs(any<Int>(), any(), any()) } called times(3)
            invocation { exampleInterface.multipleArgs(any<String>(), any(), any()) } called times(2)
            invocation { exampleInterface.multipleArgs(any<String>(), any<String>(), any()) } called once
            invocation { exampleInterface.multipleArgs(any<String>(), any<String>(), any<String>()) } called never
            invocation { exampleInterface.multipleArgs(any(), any(), eq(false)) } called once
        }

        verifyFailed { invocation { exampleInterface.multipleArgs(any(), any(), any()) } called once }
        verifyFailed { invocation { exampleInterface.multipleArgs(any<Int>(), any(), any()) } called times(2) }
        verifyFailed { invocation { exampleInterface.multipleArgs(any<String>(), any(), any()) } called times(3) }
        verifyFailed { invocation { exampleInterface.multipleArgs(any<String>(), any<String>(), any()) } called never }
        verifyFailed { invocation { exampleInterface.multipleArgs(any<String>(), any<String>(), any<String>()) } called once }
        verifyFailed { invocation { exampleInterface.multipleArgs(any(), any(), eq(false)) } called never }
    }

    @Test
    @JsName("Multiple_argument_matchers_with_list_matcher___complex_verification_scenario")
    fun `Multiple argument matchers with list matcher - complex verification scenario`() = withMMock {
        val exampleInterface = mock.ExampleInterface()

        every { exampleInterface.multipleArgs(anyList<List<Int>, Int>(), any(), any()) } returns 1

        exampleInterface.multipleArgs(DelegatedList(listOf(1, 2, 3)), 2, "")
        exampleInterface.multipleArgs(DelegatedMutableList(mutableListOf(1, 2, 3)), "", "")
        exampleInterface.multipleArgs(NewDelegatedMutableList(mutableListOf(3, 4, 5)), 7, 5)

        verify {
            invocation { exampleInterface.multipleArgs(anyList<List<Int>, Int>(), any(), any()) } called times(3)
            invocation { exampleInterface.multipleArgs(anyList<List<Int>, Int>(), any<String>(), any<String>()) } called once
            invocation { exampleInterface.multipleArgs(anyList<MutableList<Int>, Int>(), any<Int>(), any<String>()) } called never
            invocation { exampleInterface.multipleArgs(anyList<MutableList<Int>, Int>(), any(), any()) } called twice
        }

        verifyFailed { invocation { exampleInterface.multipleArgs(anyList<List<Int>, Int>(), any(), any()) } called once }
        verifyFailed { invocation { exampleInterface.multipleArgs(anyList<MutableList<Int>, Int>(), any<String>(), any()) } called twice }

        assertFailsWith<NoMethodStubException> { exampleInterface.multipleArgs(0, 0, 0) }
    }

    @Test
    @JsName("Multiple_argument_matchers_with_set_matcher___complex_verification_scenario")
    fun `Multiple argument matchers with set matcher - complex verification scenario`() = withMMock {
        val exampleInterface = mock.ExampleInterface()

        every { exampleInterface.multipleArgs(anySet<Set<Int>, Int>(), any(), any()) } returns 1

        exampleInterface.multipleArgs(DelegatedSet(setOf(1, 2, 3)), 2, "")
        exampleInterface.multipleArgs(DelegatedMutableSet(mutableSetOf(1, 2, 3)), "", "")
        exampleInterface.multipleArgs(NewDelegatedMutableSet(mutableSetOf(3, 4, 5)), 7, 3)

        verify {
            invocation { exampleInterface.multipleArgs(anySet<Set<Int>, Int>(), any(), any()) } called times(3)
            invocation { exampleInterface.multipleArgs(anySet<Set<Int>, Int>(), any<String>(), any<String>()) } called once
            invocation { exampleInterface.multipleArgs(anySet<MutableSet<Int>, Int>(), any<Int>(), any<String>()) } called never
            invocation { exampleInterface.multipleArgs(anySet<MutableSet<Int>, Int>(), any(), any()) } called twice
        }

        verifyFailed { invocation { exampleInterface.multipleArgs(anySet<Set<Int>, Int>(), any(), any()) } called once }
        verifyFailed { invocation { exampleInterface.multipleArgs(anySet<MutableSet<Int>, Int>(), any<String>(), any()) } called twice }

        assertFailsWith<NoMethodStubException> { exampleInterface.multipleArgs(0, 0, 0) }
    }

    @Test
    @JsName("Multiple_argument_matchers_with_map_matcher___complex_verification_scenario")
    fun `Multiple argument matchers with map matcher - complex verification scenario`() = withMMock {
        val exampleInterface = mock.ExampleInterface()

        every { exampleInterface.multipleArgs(anyMap<Map<Int, Int>, Int, Int>(), any(), any()) } returns 1

        exampleInterface.multipleArgs(DelegatedMap(mapOf(Pair(1, 2), Pair(3, 4))), 2, "")
        exampleInterface.multipleArgs(DelegatedMutableMap(mutableMapOf(Pair(1, 2), Pair(3, 4))), "", "")
        exampleInterface.multipleArgs(NewDelegatedMutableMap(mutableMapOf(Pair(1, 2), Pair(3, 4))), 7, 3)

        verify {
            invocation { exampleInterface.multipleArgs(anyMap<Map<Int, Int>, Int, Int>(), any(), any()) } called times(3)
            invocation { exampleInterface.multipleArgs(anyMap<Map<Int, Int>, Int, Int>(), any<String>(), any<String>()) } called once
            invocation { exampleInterface.multipleArgs(anyMap<MutableMap<Int, Int>, Int, Int>(), any<Int>(), any<String>()) } called never
            invocation { exampleInterface.multipleArgs(anyMap<MutableMap<Int, Int>, Int, Int>(), any(), any()) } called twice
        }

        verifyFailed { invocation { exampleInterface.multipleArgs(anyMap<Map<Int, Int>, Int, Int>(), any(), any()) } called once }
        verifyFailed { invocation { exampleInterface.multipleArgs(anyMap<MutableMap<Int, Int>, Int, Int>(), any<String>(), any()) } called twice }

        assertFailsWith<NoMethodStubException> { exampleInterface.multipleArgs(0, 0, 0) }
    }

    @Test
    @JsName("Multiple_collection_argument_matchers")
    fun `Multiple collection argument matchers`() = withMMock {
        val exampleInterface = mock.ExampleInterface()

        every { exampleInterface.multipleCollectionArgs(anyList(), anySet(), anyMap()) } returns ""

        exampleInterface.multipleCollectionArgs(DelegatedList(listOf(1, 2, 3)), DelegatedSet(setOf("Int", "String")), DelegatedMap())
        exampleInterface.multipleCollectionArgs(DelegatedMutableList(mutableListOf(1, 2, 3)), DelegatedMutableSet(mutableSetOf("String")), DelegatedMutableMap())
        exampleInterface.multipleCollectionArgs(NewDelegatedMutableList(), NewDelegatedMutableSet(), NewDelegatedMutableMap())
        exampleInterface.multipleCollectionArgs(DelegatedList(), DelegatedMutableSet(), DelegatedMap())

        verify {
            invocation { exampleInterface.multipleCollectionArgs(anyList(), anySet(), anyMap()) } called times(4)
            invocation { exampleInterface.multipleCollectionArgs(anyList(), anySet<MutableSet<String>, String>(), anyMap()) } called times(3)
            invocation { exampleInterface.multipleCollectionArgs(anyList(), anySet<MutableSet<String>, String>(), anyMap<MutableMap<Int, String>, Int, String>()) } called twice
            invocation { exampleInterface.multipleCollectionArgs(anyList<MutableList<Int>, Int>(), anySet(), anyMap()) } called twice
        }

        verifyFailed { invocation { exampleInterface.multipleCollectionArgs(anyList(), anySet(), anyMap()) } called times(3) }

        assertFailsWith<MMockRecordingException> {
            every { exampleInterface.multipleCollectionArgs(mutableListOf(), anySet(), anyMap()) } returns "Error"
        }
    }

    @Test
    @JsName("Implicit_equals_argument_matcher_during_recording")
    fun `Implicit equals argument matcher during recording`() = withMMock {
        val exampleInterface = mock.ExampleInterface()

        every { exampleInterface.multipleArgs(1, 2, 3) } returns 1
        every { exampleInterface.multipleCollectionArgs(listOf(1, 2), setOf("Int", "String"), mapOf(Pair(1, "String"))) } returns "String"

        assertEquals(1, exampleInterface.multipleArgs(1, 2, 3))
        assertEquals("String", exampleInterface.multipleCollectionArgs(listOf(1, 2), setOf("Int", "String"), mapOf(Pair(1, "String"))))
        assertFailsWith<NoMethodStubException> { exampleInterface.multipleArgs(0, 0, 0) }
        assertFailsWith<NoMethodStubException> { exampleInterface.multipleCollectionArgs(listOf(), setOf(), mapOf()) }

        verify {
            invocation { exampleInterface.multipleArgs(1, 2, 3) } called once
            invocation { exampleInterface.multipleCollectionArgs(listOf(1, 2), setOf("Int", "String"), mapOf(Pair(1, "String"))) } called once
            invocation { exampleInterface.multipleArgs(3, 2, 1) } called never
            invocation { exampleInterface.multipleCollectionArgs(listOf(3, 4), setOf("NotInt", "NotString"), mapOf(Pair(2, "NotString"))) } called never
        }

        verifyFailed { invocation { exampleInterface.multipleArgs(1, 2, 3) } called never }
        verifyFailed { invocation { exampleInterface.multipleCollectionArgs(listOf(1, 2), setOf("Int", "String"), mapOf(Pair(1, "String"))) } called never }
        verifyFailed { invocation { exampleInterface.multipleArgs(3, 2, 1) } called once }
        verifyFailed { invocation { exampleInterface.multipleCollectionArgs(listOf(3, 4), setOf("NotInt", "NotString"), mapOf(Pair(2, "NotString"))) } called once }
    }

    @Test
    @JsName("Error_is_thrown_in_case_of_mixed_matcher_usage")
    fun `Error is thrown in case of mixed matcher usage`() = withMMock {
        val exampleInterface = mock.ExampleInterface()

        assertFailsWith<MMockRecordingException> {
            every { exampleInterface.multipleArgs(1, any(), 3) } returns 1
            every { exampleInterface.multipleCollectionArgs(listOf(1, 2), anySet(), anyMap()) }
        }

        assertFailsWith<MMockRecordingException> {
            verify {
                invocation { exampleInterface.multipleArgs(any(), 2, 3) } called once
                invocation { exampleInterface.multipleCollectionArgs(anyList(), setOf("Int", "String"), anyMap()) } called once
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
