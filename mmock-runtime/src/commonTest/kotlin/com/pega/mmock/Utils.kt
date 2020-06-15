/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock

import com.pega.mmock.dsl.MMockContext
import com.pega.mmock.dsl.VerificationContext
import kotlin.test.assertFailsWith

suspend fun MMockContext.verifyFailed(block: suspend VerificationContext.() -> Unit) {
    assertFailsWith<MMockVerificationException> {
        verify { block() }
    }
}
