package com.github.virelion.mmock.samples

import com.github.virelion.mmock.backend.MockContainer
import com.github.virelion.mmock.backend.ObjectMock
import com.github.virelion.mmock.dsl.MMockContext

interface ExampleInterface {
    fun noargsFunction(): Int
    fun function(arg1: Int): Int

    suspend fun suspendFun(arg1: Int): Int
}

class ExampleMock(override val mMockContext: MMockContext): ObjectMock, ExampleInterface {
    override val mocks: MockContainer = MockContainer(this)

    override fun noargsFunction(): Int {
        return mocks.invoke<Int>("noargsFunction")
    }

    override fun function(arg1: Int): Int {
        return mocks.invoke<Int>("function", arg1)
    }

    override suspend fun suspendFun(arg1: Int): Int {
        return mocks.invokeSuspend<Int>("function", arg1)
    }
}