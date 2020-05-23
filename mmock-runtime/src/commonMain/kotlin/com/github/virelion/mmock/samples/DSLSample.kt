package com.github.virelion.mmock.samples

import com.github.virelion.mmock.dsl.any
import com.github.virelion.mmock.dsl.eq
import com.github.virelion.mmock.dsl.instanceOf
import com.github.virelion.mmock.withMMock

class DSLSample {
    fun s1() = withMMock {
        val exampleInterface = mock.ExampleInterface()

        every { exampleInterface.noArgsFunction() } returns 1
        every { exampleInterface.function(any()) } returns 1
        every { exampleInterface.function(instanceOf<Int>()) } returns 1
        every { exampleInterface.function(eq(1)) } returns 1
    }
}
