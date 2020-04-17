package com.github.virelion.mmock.samples

import com.github.virelion.mmock.dsl.*
import com.github.virelion.mmock.withMMock

class DSLSample {
    fun s1() = withMMock {
        val exampleInterface = mmock<ExampleInterface>()

        every { exampleInterface.noArgsFunction() } returns 1
        every { exampleInterface.function(any()) } returns 1
        every { exampleInterface.function(instanceOf<Int>()) } returns 1
        every { exampleInterface.function(eq(1)) } returns 1
    }
}