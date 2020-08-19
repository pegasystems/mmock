/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.demo

import com.pega.mmock.GenerateMock

@GenerateMock
interface ExampleInterface {

    fun simple()
    fun function(arg: Int): Int?
    suspend fun suspendFunction(arg: Int?): Int?
    fun <T> genericFunction(arg: T): T
    fun <A, B, C> complicatedGenericFunction(map: Map<A, List<out List<C>>>)
    fun withAsterix(list: List<*>): List<*>
}
