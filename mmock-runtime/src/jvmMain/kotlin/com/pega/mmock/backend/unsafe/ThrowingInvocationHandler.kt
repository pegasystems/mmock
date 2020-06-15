/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.backend.unsafe

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

class ThrowingInvocationHandler : InvocationHandler {
    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any {
        throw IllegalAccessException("Invoked unsafe instance")
    }
}
