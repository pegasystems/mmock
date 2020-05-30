package com.github.virelion.mmock.dsl

import com.github.virelion.mmock.MMockVerificationException
import com.github.virelion.mmock.backend.RecordLog
import com.github.virelion.mmock.backend.stack.Invocation

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
