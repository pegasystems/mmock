/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.demo

import com.pega.mmock.dsl.any
import com.pega.mmock.dsl.anyList
import com.pega.mmock.dsl.anyMap
import com.pega.mmock.dsl.anySet
import com.pega.mmock.dsl.once
import com.pega.mmock.dsl.times
import com.pega.mmock.dsl.twice
import com.pega.mmock.withMMock
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame
import kotlin.test.assertTrue

class ExampleClassTest {

    @Test
    fun simple() = withMMock {
        val myMock = mock.ExampleClass()
        every { myMock.simple() } returns Unit
        myMock.simple()
    }

    @Test
    fun function() = withMMock {
        val myMock = mock.ExampleClass()
        every { myMock.function(any()) } returns 2
        assertEquals(2, myMock.function(42))
        verify { invocation { myMock.function(42) } called once }
    }

    @Test
    fun functionWithList() = withMMock {
        class DelegatedList<T>(private val delegate: List<T> = listOf()) : List<T> by delegate {
            override fun equals(other: Any?): Boolean {
                if (other is DelegatedList<*>)
                    return this.delegate.equals(other.delegate)
                return false
            }
        }

        class DelegatedMutableList<T>(private val delegate: MutableList<T> = mutableListOf()) : MutableList<T> by delegate {
            override fun equals(other: Any?): Boolean {
                if (other is DelegatedMutableList<*>)
                    return this.delegate.equals(other.delegate)
                return false
            }
        }

        val myMock = mock.ExampleClass()
        every { myMock.functionWithList(anyList<MutableList<Int>, Int>()) } returns 5
        every { myMock.functionWithList(anyList()) } returns 3
        assertEquals(3, myMock.functionWithList(DelegatedList(listOf(1, 2))))
        assertEquals(5, myMock.functionWithList(DelegatedMutableList(mutableListOf(1, 2))))
        verify {
            invocation { myMock.functionWithList(DelegatedList(listOf(1, 2))) } called once
            invocation { myMock.functionWithList(DelegatedMutableList(mutableListOf(1, 2))) } called once
            invocation { myMock.functionWithList(anyList()) } called twice
        }
    }

    @Test
    fun functionWithSet() = withMMock {
        class DelegatedSet<T>(private val delegate: Set<T> = setOf()) : Set<T> by delegate {
            override fun equals(other: Any?): Boolean {
                if (other is DelegatedSet<*>)
                    return this.delegate.equals(other.delegate)
                return false
            }
        }

        class DelegatedMutableSet<T>(private val delegate: MutableSet<T> = mutableSetOf()) : MutableSet<T> by delegate {
            override fun equals(other: Any?): Boolean {
                if (other is DelegatedMutableSet<*>)
                    return this.delegate.equals(other.delegate)
                return false
            }
        }

        val myMock = mock.ExampleClass()
        every { myMock.functionWithSet(anySet<MutableSet<Int>, Int>()) } returns 5
        every { myMock.functionWithSet(anySet()) } returns 3
        assertEquals(3, myMock.functionWithSet(DelegatedSet(setOf(1, 2))))
        assertEquals(5, myMock.functionWithSet(DelegatedMutableSet(mutableSetOf(1, 2))))
        verify {
            invocation { myMock.functionWithSet(DelegatedSet(setOf(1, 2))) } called once
            invocation { myMock.functionWithSet(DelegatedMutableSet(mutableSetOf(1, 2))) } called once
            invocation { myMock.functionWithSet(anySet()) } called twice
        }
    }

    @Test
    fun functionWithMap() = withMMock {
        class DelegatedMap<T, E>(private val delegate: Map<T, E> = mapOf()) : Map<T, E> by delegate {
            override fun equals(other: Any?): Boolean {
                if (other is DelegatedMap<*, *>)
                    return this.delegate.equals(other.delegate)
                return false
            }
        }

        class DelegatedMutableMap<T, E>(private val delegate: MutableMap<T, E> = mutableMapOf()) : MutableMap<T, E> by delegate {
            override fun equals(other: Any?): Boolean {
                if (other is DelegatedMutableMap<*, *>)
                    return this.delegate.equals(other.delegate)
                return false
            }
        }

        val myMock = mock.ExampleClass()
        every { myMock.functionWithMap(anyMap<MutableMap<Int, String>, Int, String>()) } returns 5
        every { myMock.functionWithMap(anyMap()) } returns 3
        assertEquals(3, myMock.functionWithMap(DelegatedMap(mapOf(Pair(1, "String"), Pair(2, "Int")))))
        assertEquals(5, myMock.functionWithMap(DelegatedMutableMap(mutableMapOf(Pair(1, "String")))))
        verify {
            invocation { myMock.functionWithMap(DelegatedMap(mapOf(Pair(1, "String"), Pair(2, "Int")))) } called once
            invocation { myMock.functionWithMap(DelegatedMutableMap(mutableMapOf(Pair(1, "String")))) } called once
            invocation { myMock.functionWithMap(anyMap()) } called twice
        }
    }

    @Test
    fun suspendFunction() = withMMock {
        val myMock = mock.ExampleClass()
        every { myMock.suspendFunction(any()) } returns 2
        assertEquals(2, myMock.suspendFunction(42))
        verify { invocation { myMock.suspendFunction(42) } called once }
    }

    @Test
    fun genericFunction() = withMMock {
        val myMock = mock.ExampleClass()
        every { myMock.genericFunction<Any>(any()) } returns 2
        assertEquals(2, myMock.genericFunction(42))
        verify { invocation { myMock.genericFunction(42) } called once }
    }

    @Test
    fun complicatedGenericFunction() = withMMock {
        val myMock = mock.ExampleClass()
        every { myMock.complicatedGenericFunction<Any, Any, Any>(any()) } returns Unit
        myMock.complicatedGenericFunction<Any, Any, Any>(mapOf())
        verify { invocation { myMock.complicatedGenericFunction<Any, Any, Any>(any()) } called once }
    }

    @Test
    fun functionWithTypeOutsideOfProject() = withMMock {
        val myMock = mock.ExampleClass()
        every { myMock.functionWithTypeOutsideOfProject(any()) } returns myMock
        assertSame(myMock, myMock.functionWithTypeOutsideOfProject(myMock))
        verify { invocation { myMock.functionWithTypeOutsideOfProject(any()) } called once }
    }

    @Test
    fun testMutableProperty() = withMMock {
        val myMock = mock.ExampleClass()
        every { myMock.mutableProperty = any() } returns Unit
        every { myMock.mutableProperty } returns myMock

        assertSame(myMock.mutableProperty, myMock)
        myMock.mutableProperty = null
        assertSame(myMock.mutableProperty, myMock)

        verify {
            invocation { myMock.mutableProperty } called twice
            invocation { myMock.mutableProperty = any() } called once
        }
    }

    @Test
    fun testImmutable() = withMMock {
        val myMock = mock.ExampleClass()
        every { myMock.property } returns myMock
        assertSame(myMock.property, myMock)

        verify {
            invocation { myMock.property } called once
        }
    }

    @Test
    fun withAsterix() = withMMock {
        val myMock = mock.ExampleClass()
        every { myMock.withAsterix(any()) } returns listOf<Any>()
        assertTrue(myMock.withAsterix(listOf<Any>()).isEmpty())

        verify {
            invocation { myMock.withAsterix(any()) } called once
        }
    }
}
