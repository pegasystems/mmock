package com.github.virelion.mmock.dsl

import com.github.virelion.mmock.backend.stack.InvocationCountRuleElement
import com.github.virelion.mmock.backend.stack.StackElement

interface VerificationContext {
    var recordingStack: MutableList<StackElement>?

    @MMockDSL
    infix fun Any?.called(rule: InvocationMatcher) {
        recordingStack?.add(InvocationCountRuleElement(rule))
    }
}