/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.dsl

import com.pega.mmock.MMockVerificationException
import com.pega.mmock.backend.RecordLog
import com.pega.mmock.backend.stack.Invocation

@MMockDSL
class SequenceVerificationContext(
    private val recordLog: RecordLog,
    private val recordingContext: RecordingContext
) : RecordingContext by recordingContext, InvocationProtection {

    @MMockDSL
    internal suspend fun verify(block: suspend SequenceVerificationContext.() -> Unit) {
        verifySequence(
                record {
                    block()
                }
        )
    }

    private fun verifySequence(invocations: List<Invocation<*>>) {
        if (invocations.isEmpty()) return

        val invocationsArray = invocations.toTypedArray()
        val recordsArray = recordLog.log.toTypedArray()

        var i = 0
        while (i <= recordsArray.size - invocationsArray.size) {
            var j = 0
            while (j < invocationsArray.size) {
                if (!recordsArray[i + j].fits(invocationsArray[j])) {
                    break
                }
                j++
            }

            if (j == invocationsArray.size) return

            i++
        }
        throw MMockVerificationException("Sequence verification failed")
    }
}
