package com.github.virelion.mmock.samples

import com.github.virelion.mmock.dsl.*

class DSLSample {
    interface ExampleInterface {
        val valueProperty: String
        var variableProperty: String

        fun noargsFunction(): Int
        fun function(arg1: Int): Int
    }

    fun s1() = withMMock {
        val exampleInterface = mmock<ExampleInterface>()

        every { exampleInterface.noargsFunction() } returns 1
        every { exampleInterface.function(any()) } returns 1
        every { exampleInterface.function(instanceOf<Int>()) } returns 1
        every { exampleInterface.function(eq(1)) } returns 1


        verify {
            exampleInterface.noargsFunction() called once
            exampleInterface.noargsFunction() called twice
            exampleInterface.noargsFunction() called times(3)
            exampleInterface.noargsFunction() called never
            exampleInterface.noargsFunction() called { it > 2 }
        }
    }
}