/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.dsl

import com.pega.mmock.RecordingDoneMarker

interface InvocationProtection {
    class InvocationVerification internal constructor()

    private val invocationVerificationMarker get() = InvocationVerification()

    @MMockDSL
    suspend fun invocation(block: suspend () -> Unit): InvocationVerification {
        try {
            block()
        } catch (e: RecordingDoneMarker) {
            // do nothing
        }
        return invocationVerificationMarker
    }
}
