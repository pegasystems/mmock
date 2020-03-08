package com.github.virelion.mmock.backend.stack

import com.github.virelion.mmock.backend.ArgumentsVerificationFunction
import com.github.virelion.mmock.backend.ObjectMock

sealed class StackElement {
    abstract fun visit(currentInvocation: Invocation<*>, stack: List<Invocation<*>>)
}

class ArgumentStackElement(private val argumentsVerificationFunction: ArgumentsVerificationFunction) : StackElement() {
    override fun visit(currentInvocation: Invocation<*>, stack: List<Invocation<*>>) {
        currentInvocation.args.add(argumentsVerificationFunction)
    }
}

class MethodElement(
        private val name: String,
        private val objectMock: ObjectMock
): StackElement() {
    override fun visit(currentInvocation: Invocation<*>, stack: List<Invocation<*>>) {
        currentInvocation.name = name
        currentInvocation.objectMock = objectMock
    }
}

class InvocationCountRuleElement(
        val rule: (Int) -> Boolean
): StackElement() {
    override fun visit(currentInvocation: Invocation<*>, stack: List<Invocation<*>>) {
        stack.last().invocationConstraint = rule
    }
}