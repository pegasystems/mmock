package com.github.virelion.mmock.dsl

import com.github.virelion.mmock.RecordingDoneMarker
import com.github.virelion.mmock.backend.stack.InvocationCountRuleElement
import com.github.virelion.mmock.backend.stack.StackElement

class InvocationVerification internal constructor()
private val invocationVerificationMarker get() = InvocationVerification()

interface VerificationContext {
    var recordingStack: MutableList<StackElement>?

    @MMockDSL
    infix fun InvocationVerification.called(rule: InvocationMatcher) {
        recordingStack?.add(InvocationCountRuleElement(rule))
    }

    @MMockDSL
    suspend fun invocation(block: suspend () -> Unit): InvocationVerification {
        try {
            block()
        } catch(e: RecordingDoneMarker) {

        }
        return invocationVerificationMarker
    }
}