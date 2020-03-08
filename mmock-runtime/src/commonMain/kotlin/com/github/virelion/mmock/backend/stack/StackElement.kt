package com.github.virelion.mmock.backend.stack

import com.github.virelion.mmock.MMockRecordingException
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
        private val objectMock: ObjectMock,
        private val args: Array<out Any?>
): StackElement() {
    override fun visit(currentInvocation: Invocation<*>, stack: List<Invocation<*>>) {
        currentInvocation.name = name
        currentInvocation.objectMock = objectMock

        if(currentInvocation.args.count() != args.size && args.isNotEmpty()) {
            if(currentInvocation.args.count() != 0) throw MMockRecordingException("Matcher and argument counts are different. Please use all matcher or all argument notation.")
            currentInvocation.args = args.map { item -> { it:Any? -> it == item } }.toMutableList()
        }
    }
}

class InvocationCountRuleElement(
        val rule: (Int) -> Boolean
): StackElement() {
    override fun visit(currentInvocation: Invocation<*>, stack: List<Invocation<*>>) {
        stack.last().invocationConstraint = rule
    }
}