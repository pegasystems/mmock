/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock

import com.pega.mmock.backend.runSuspend
import com.pega.mmock.dsl.MMockContext

actual fun withMMock(block: suspend MMockContext.() -> Unit) = runSuspend {
    MMockContext().apply { block() }
}
