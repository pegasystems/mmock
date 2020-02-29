package com.github.virelion.mmock.backend.stack

import com.github.virelion.mmock.backend.ArgumentsVerificationFunction
import com.github.virelion.mmock.backend.ObjectMock

sealed class StackElement {
    abstract fun visit(invocation: Invocation<*>)
}

class ArgumentStackElement(private val argumentsVerificationFunction: ArgumentsVerificationFunction) : StackElement() {
    override fun visit(invocation: Invocation<*>) {
        invocation.args.add(argumentsVerificationFunction)
    }
}

class MethodElement(
        private val name: String,
        private val objectMock: ObjectMock
): StackElement() {
    override fun visit(invocation: Invocation<*>) {
        invocation.name = name
        invocation.objectMock = objectMock
    }
}