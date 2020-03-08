package com.github.virelion.mmock.samples

import com.github.virelion.mmock.backend.MockContainer
import com.github.virelion.mmock.backend.ObjectMock
import com.github.virelion.mmock.dsl.MMockContext

interface ExampleInterface {
    fun noargsFunction(): Int
    fun function(arg1: Int): Int
    fun functionAny(arg1: Any): Any

    fun multipleArgs(arg1: Any, arg2: Any, arg3: Any): Any

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

    override fun functionAny(arg1: Any): Any {
        return mocks.invoke("functionAny", arg1)
    }

    override fun multipleArgs(arg1: Any, arg2: Any, arg3: Any): Any {
        return mocks.invoke("multipleArgs", arg1, arg2, arg3)
    }

    override suspend fun suspendFun(arg1: Int): Int {
        return mocks.invokeSuspend<Int>("suspendFun", arg1)
    }
}