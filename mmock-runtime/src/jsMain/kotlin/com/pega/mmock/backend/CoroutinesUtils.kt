/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.backend

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise

actual fun runSuspend(block: suspend () -> Unit): dynamic {
    return GlobalScope.promise {
        block()
    }
}
