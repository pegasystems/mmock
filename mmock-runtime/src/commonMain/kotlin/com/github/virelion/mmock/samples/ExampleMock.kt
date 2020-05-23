package com.github.virelion.mmock.samples

import com.github.virelion.mmock.backend.MockContainer
import com.github.virelion.mmock.backend.ObjectMock
import com.github.virelion.mmock.dsl.MMockContext
import com.github.virelion.mmock.dsl.MockInitializer

interface ExampleInterface {
    val property: Int?
    var mutableProperty: Int?

    fun noArgsFunction(): Int
    fun function(arg1: Int): Int
    fun functionAny(arg1: Any): Any
    fun multipleArgs(arg1: Any, arg2: Any, arg3: Any): Any

    suspend fun suspendNoArgsFunction(): Int
    suspend fun suspendFunction(arg1: Int): Int
    suspend fun suspendFunctionAny(arg1: Any): Any
    suspend fun suspendMultipleArgs(arg1: Any, arg2: Any, arg3: Any): Any
}

fun MockInitializer.ExampleInterface(): ExampleInterface {
    return ExampleMock(context)
}

class ExampleMock(override val mMockContext: MMockContext) : ObjectMock, ExampleInterface {
    override val mocks: MockContainer = MockContainer(this)

    override val property: Int?
        get() { return mocks.invoke("`property`(GET, property)") }
    override var mutableProperty: Int?
        get() { return mocks.invoke("`property`(GET, mutableProperty)") }
        set(value) { return mocks.invoke("`property`(SET, mutableProperty)", value) }

    override fun noArgsFunction(): Int {
        return mocks.invoke<Int>("noArgsFunction")
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

    override suspend fun suspendNoArgsFunction(): Int {
        return mocks.invoke("suspendNoArgsFunction")
    }

    override suspend fun suspendFunction(arg1: Int): Int {
        return mocks.invoke("suspendFunction", arg1)
    }

    override suspend fun suspendFunctionAny(arg1: Any): Any {
        return mocks.invoke("suspendFunctionAny", arg1)
    }

    override suspend fun suspendMultipleArgs(arg1: Any, arg2: Any, arg3: Any): Any {
        return mocks.invoke("suspendMultipleArgs", arg1, arg2, arg3)
    }
}
