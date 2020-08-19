/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.demo

import com.pega.mmock.GenerateMock
import com.pega.mmock.secondpackage.BaseInterface

@GenerateMock
class ExampleClass(
    val arg1: Array<Int>?,
    val arg2: List<Int>?,
    val arg3: MutableList<Int> = mutableListOf(),
    val arg5: Set<Int>,
    val arg6: MutableSet<Int>,
    val arg9: Map<Int, Int>,
    val arg11: Int,
    val arg12: ULong,
    val arg10: MutableMap<Int, Int>
) {

    fun simple() {
        return
    }

    fun function(arg: Int): Int? {
        return 3
    }

    fun functionWithList(arg: List<Int>): Int {
        return 7
    }

    fun functionWithSet(arg: Set<Int>): Int {
        return 10
    }

    suspend fun functionWithMap(arg: Map<Int, String>): Int {
        return 11
    }

    suspend fun suspendFunction(arg: Int?): Int? {
        return 4
    }

    fun <T> genericFunction(arg: T): T {
        return arg
    }

    fun <A, B, C> complicatedGenericFunction(map: Map<A, List<out List<C>>>) {
        return
    }

    fun withAsterix(list: List<*>): List<*> {
        return list
    }
}
