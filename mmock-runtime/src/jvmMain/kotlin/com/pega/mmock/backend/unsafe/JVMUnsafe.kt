/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.backend.unsafe

import java.lang.reflect.Field
import sun.misc.Unsafe

object JVMUnsafe {
    val unsafe: Unsafe

    init {
        val f: Field = Unsafe::class.java.getDeclaredField("theUnsafe")
        f.isAccessible = true
        unsafe = f.get(null) as Unsafe
    }

    inline fun <reified T> createUnsafeIntsanceOfType(): T {
        return unsafe.allocateInstance(T::class.java) as T
    }
}
