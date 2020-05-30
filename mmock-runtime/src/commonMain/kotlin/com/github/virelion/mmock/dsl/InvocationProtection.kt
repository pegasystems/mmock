package com.github.virelion.mmock.dsl

import com.github.virelion.mmock.RecordingDoneMarker

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
