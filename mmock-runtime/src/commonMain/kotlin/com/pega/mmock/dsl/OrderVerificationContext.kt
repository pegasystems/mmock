/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.dsl

import com.pega.mmock.MMockVerificationException
import com.pega.mmock.backend.RecordLog
import com.pega.mmock.backend.stack.Invocation

@MMockDSL
class OrderVerificationContext(
    private val recordLog: RecordLog,
    private val recordingContext: RecordingContext
) : RecordingContext by recordingContext, InvocationProtection {
    @MMockDSL
    internal suspend fun verify(block: suspend OrderVerificationContext.() -> Unit) {
        verifyOrder(
                record {
                    block()
                }
        )
    }

    private fun verifyOrder(invocations: List<Invocation<*>>) {
        if (invocations.isEmpty()) return

        val invocationsIterator = invocations.iterator()
        val recordIterator = recordLog.log.iterator()

        while (invocationsIterator.hasNext()) {
            val invocation = invocationsIterator.next()
            recordIterator.first { it.fits(invocation) }
                    ?: throw MMockVerificationException("Order verification failed")
        }
    }

    private fun <T> Iterator<T>.first(constraint: (T) -> Boolean): T? {
        while (hasNext()) {
            val next = next()
            if (constraint(next)) {
                return next
            }
        }
        return null
    }
}
