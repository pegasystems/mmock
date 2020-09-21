/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.dsl

import com.pega.mmock.MMockVerificationException
import com.pega.mmock.backend.RecordLog
import com.pega.mmock.backend.stack.Invocation
import com.pega.mmock.backend.stack.InvocationCountRuleElement

@MMockDSL
class VerificationContext(
    private val recordLog: RecordLog,
    private val recordingContext: RecordingContext
) : RecordingContext by recordingContext, InvocationProtection {
    @MMockDSL
    infix fun InvocationProtection.InvocationVerification.called(rule: InvocationMatcher) {
        recordingStack?.add(InvocationCountRuleElement(rule))
    }

    /**
     * Verify sequence of statements inside the [block].
     */
    @MMockDSL
    suspend fun sequence(block: suspend SequenceVerificationContext.() -> Unit) {
        SequenceVerificationContext(recordLog, recordingContext).verify(block)
    }

    /**
     * Verify order of statements inside the [block].
     */
    @MMockDSL
    suspend fun order(block: suspend OrderVerificationContext.() -> Unit) {
        OrderVerificationContext(recordLog, recordingContext).verify(block)
    }

    internal suspend fun verify(block: suspend VerificationContext.() -> Unit) {
        val invocations = record { block() }
        verifyInvocations(invocations)
    }

    private fun verifyInvocations(invocations: List<Invocation<*>>) {
        invocations.forEach { invocation ->
            val invocationAmount = recordLog.log.count {
                it.fits(invocation)
            }
            val rule = invocation.invocationConstraint ?: { it == 1 }
            if (!rule(invocationAmount)) throw MMockVerificationException("Function ${invocation.name} invoked $invocationAmount times")
        }
    }
}
