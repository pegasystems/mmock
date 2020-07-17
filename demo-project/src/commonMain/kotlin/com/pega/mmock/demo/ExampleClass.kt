/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.demo

import com.pega.mmock.GenerateMock

@GenerateMock
open class ExampleClass(open val test: Int = 7) : BaseInterface {
    open val property: BaseInterface = NotMocked()
    open var mutableProperty: BaseInterface? = NotMocked()

    open fun simple() {
        return
    }

    open fun function(arg: Int): Int? {
        return 3
    }

    open suspend fun suspendFunction(arg: Int?): Int? {
        return 4
    }

    open fun <T> genericFunction(arg: T): T {
        return arg
    }

    open fun <A, B, C> complicatedGenericFunction(map: Map<A, List<out List<C>>>) {
        return
    }

    open fun withAsterix(list: List<*>): List<*> {
        return list
    }

    open fun functionWithTypeOutsideOfProject(arg: BaseInterface): BaseInterface {
        return arg
    }

    override fun baseFunction(): Int {
        return 10
    }
}
