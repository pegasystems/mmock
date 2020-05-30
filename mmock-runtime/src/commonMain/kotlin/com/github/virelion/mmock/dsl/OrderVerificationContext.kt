package com.github.virelion.mmock.dsl

import com.github.virelion.mmock.MMockVerificationException
import com.github.virelion.mmock.backend.RecordLog
import com.github.virelion.mmock.backend.stack.Invocation

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
