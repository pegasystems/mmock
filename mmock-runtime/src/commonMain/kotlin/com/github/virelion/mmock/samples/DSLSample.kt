package com.github.virelion.mmock.samples

import com.github.virelion.mmock.backend.MockContainer
import com.github.virelion.mmock.backend.ObjectMock
import com.github.virelion.mmock.backend.runSuspend
import com.github.virelion.mmock.dsl.*

class DSLSample {
    fun s1() = withMMock {
        val exampleInterface = mmock<ExampleInterface>()

        every { exampleInterface.noargsFunction() } returns 1
        every { exampleInterface.function(any()) } returns 1
        every { exampleInterface.function(instanceOf<Int>()) } returns 1
        every { exampleInterface.function(eq(1)) } returns 1
    }
}