package com.github.virelion.mmock.demo

import com.github.virelion.mmock.dsl.any
import com.github.virelion.mmock.dsl.once
import com.github.virelion.mmock.dsl.times
import com.github.virelion.mmock.dsl.twice
import com.github.virelion.mmock.withMMock
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertSame

class ExampleInterfaceTest {
    @Test
    fun simple() = withMMock {
        val myMock = mock.ExampleInterface()
        every { myMock.simple() } returns Unit
        myMock.simple()
    }

    @Test
    fun function() = withMMock {
        val myMock: ExampleInterface = mock.ExampleInterface()
        every { myMock.function(any()) } returns 2
        assertEquals(2, myMock.function(42))
        verify { invocation { myMock.function(42) } called once }
    }

    @Test
    fun suspendFunction() = withMMock {
        val myMock: ExampleInterface = mock.ExampleInterface()
        every { myMock.suspendFunction(any()) } returns 2
        assertEquals(2, myMock.suspendFunction(42))
        verify { invocation { myMock.suspendFunction(42) } called once }
    }

    @Test
    fun genericFunction() = withMMock {
        val myMock: ExampleInterface = mock.ExampleInterface()
        every { myMock.genericFunction<Any>(any()) } returns 2
        assertEquals(2, myMock.genericFunction(42))
        verify { invocation { myMock.genericFunction(42) } called once }
    }

    @Test
    fun complicatedGenericFunction() = withMMock {
        val myMock: ExampleInterface = mock.ExampleInterface()
        every { myMock.complicatedGenericFunction<Any, Any, Any>(any()) } returns Unit
        myMock.complicatedGenericFunction<Any, Any, Any>(mapOf())
        verify { invocation { myMock.complicatedGenericFunction<Any, Any, Any>(any()) } called once }
    }

    @Test
    fun functionWithTypeOutsideOfProject() = withMMock {
        val myMock: ExampleInterface = mock.ExampleInterface()
        every { myMock.functionWithTypeOutsideOfProject(any()) } returns myMock
        assertSame(myMock, myMock.functionWithTypeOutsideOfProject(myMock))
        verify { invocation { myMock.functionWithTypeOutsideOfProject(any()) } called once }
    }

    @Test
    fun testMutableProperty() = withMMock {
        val myMock: ExampleInterface = mock.ExampleInterface()
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
        val myMock: ExampleInterface = mock.ExampleInterface()
        every { myMock.property } returns myMock
        assertSame(myMock.property, myMock)

        verify {
            invocation { myMock.property } called once
        }
    }
}