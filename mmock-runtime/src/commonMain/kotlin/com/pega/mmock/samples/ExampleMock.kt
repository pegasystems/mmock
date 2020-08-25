/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.samples

import com.pega.mmock.backend.MockContainer
import com.pega.mmock.backend.ObjectMock
import com.pega.mmock.dsl.MMockContext
import com.pega.mmock.dsl.MockInitializer

interface ExampleInterface {
    val property: Int?
    var mutableProperty: Int?

    fun noArgsFunction(): Int
    fun function(arg1: Int): Int
    fun functionString(arg1: String): Int
    fun functionArray(arg1: Array<Int>): Int
    fun functionList(arg1: List<Int>): Int
    fun functionMap(arg1: Map<Int, Int>): Int
    fun functionSet(arg1: Set<Int>): Int
    fun functionAny(arg1: Any): Any
    fun multipleArgs(arg1: Any, arg2: Any, arg3: Any): Any
    fun multipleCollectionArgs(arg1: List<Int>, arg2: Set<String>, arg3: Map<Int, String>): String

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

    override fun functionString(arg1: String): Int {
        return mocks.invoke("functionString", arg1)
    }

    override fun functionArray(arg1: Array<Int>): Int {
        return mocks.invoke("functionArray", arg1)
    }

    override fun functionList(arg1: List<Int>): Int {
        return mocks.invoke("functionList", arg1)
    }

    override fun functionMap(arg1: Map<Int, Int>): Int {
        return mocks.invoke("functionMap", arg1)
    }

    override fun functionSet(arg1: Set<Int>): Int {
        return mocks.invoke("functionSet", arg1)
    }

    override fun functionAny(arg1: Any): Any {
        return mocks.invoke("functionAny", arg1)
    }

    override fun multipleArgs(arg1: Any, arg2: Any, arg3: Any): Any {
        return mocks.invoke("multipleArgs", arg1, arg2, arg3)
    }

    override fun multipleCollectionArgs(arg1: List<Int>, arg2: Set<String>, arg3: Map<Int, String>): String {
        return mocks.invoke("multipleCollectionArgs", arg1, arg2, arg3)
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
