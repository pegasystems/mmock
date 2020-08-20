/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.backend.unsafe

import java.lang.reflect.Proxy
import net.sf.cglib.proxy.Enhancer
import net.sf.cglib.proxy.FixedValue

actual inline fun <reified T> createUnsafe(): T {
    return if (T::class.isAbstract) {
        Enhancer.create(T::class.java, FixedValue { }) as T
    } else if (T::class.java.isInterface) {
        Proxy.newProxyInstance(
            T::class.java.classLoader,
            arrayOf(T::class.java),
            ThrowingInvocationHandler()
        ) as T
    } else {
        JVMUnsafe.createUnsafeIntsanceOfType()
    }
}
