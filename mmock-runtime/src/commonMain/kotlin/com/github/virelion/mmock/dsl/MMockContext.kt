package com.github.virelion.mmock.dsl

import com.github.virelion.mmock.MMockVerificationException
import com.github.virelion.mmock.backend.*
import com.github.virelion.mmock.backend.stack.Invocation
import com.github.virelion.mmock.backend.stack.MethodElement
import com.github.virelion.mmock.backend.stack.StackElement

class MMockContext: VerificationContext {
    enum class State {
        RECORDING, INVOKING
    }

    var state: State = State.INVOKING
        private set

    @PublishedApi
    internal val invocationLogRecord: MutableList<InvocationLogRecord> = mutableListOf()

    override var recordingStack: MutableList<StackElement>? = null

    private fun <R> record(block: MMockContext.() -> R): List<Invocation<R>> {
        try {
            state = State.RECORDING
            val finalEventStack = mutableListOf<StackElement>()
            recordingStack = finalEventStack
            block()
            recordingStack = null

            val invocations = mutableListOf<Invocation<R>>()
            var invocation = Invocation<R>()

            finalEventStack.forEach {
                it.visit(invocation, invocations)
                if(it is MethodElement) {
                    invocations.add(invocation)
                    invocation = Invocation()
                }
            }

            return invocations
        } finally {
            state = State.INVOKING
            recordingStack = null
        }
    }

    @MMockDSL
    fun <R> every(block: MMockContext.() -> R): StubbingContext<R> {
        return StubbingContext(record(block))
    }

    @MMockDSL
    fun verify(block: VerificationContext.() -> Unit) {
        val invocations = record(block)
        invocations.forEach { invocation ->
            val invocationAmount = invocationLogRecord.count {
                invocation.objectMock === it.objectMock &&
                invocation.name == it.methodName &&
                invocation.args.verify(it.args)
            }
            val rule = invocation.invocationConstraint ?: times(1)
            if(!rule(invocationAmount)) throw MMockVerificationException("Function ${invocation.name} invoked $invocationAmount times")
        }
    }

    @MMockDSL
    infix fun <R> StubbingContext<R>.returns(value: R) {
        invocations.forEach {invocation ->
            val objectMock = requireNotNull(invocation.objectMock)
            val name = requireNotNull(invocation.name)

            objectMock.mocks.regular[name].add(FunctionMock(invocation.args) { value })
        }
    }
}

