/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.demo

import com.pega.mmock.GenerateMock

@GenerateMock
class ExampleClass() : BaseInterface {
    val property: BaseInterface = NotMocked()
    var mutableProperty: BaseInterface? = NotMocked()

    fun simple() {
        return
    }

    fun function(arg: Int): Int? {
        return 3
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

    fun functionWithTypeOutsideOfProject(arg: BaseInterface): BaseInterface {
        return arg
    }

    override fun baseFunction(): Int {
        return 10
    }
}
