package com.github.virelion.mmock.dsl

import com.github.virelion.mmock.MMockStubbingException
import com.github.virelion.mmock.MMockVerificationException
import com.github.virelion.mmock.RecordingDoneMarker
import com.github.virelion.mmock.backend.FunctionMock
import com.github.virelion.mmock.backend.InvocationLogRecord
import com.github.virelion.mmock.backend.StubbingContext
import com.github.virelion.mmock.backend.stack.Invocation
import com.github.virelion.mmock.backend.stack.MethodElement
import com.github.virelion.mmock.backend.stack.StackElement
import com.github.virelion.mmock.backend.verify

class MMockContext: VerificationContext {
    enum class State {
        RECORDING, INVOKING
    }

    var state: State = State.INVOKING
        private set

    @PublishedApi
    internal val invocationLogRecord: MutableList<InvocationLogRecord> = mutableListOf()

    override var recordingStack: MutableList<StackElement>? = null

    @MMockDSL
    val mock = MockInitializer(this)

    private suspend fun <R> record(block:  suspend MMockContext.() -> R): List<Invocation<R>> {
        try {
            state = State.RECORDING
            val finalEventStack = mutableListOf<StackElement>()
            recordingStack = finalEventStack
            try {
                block()
            } catch (e: RecordingDoneMarker) {
                // do nothing
            }
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
    suspend fun <R> every(block: suspend MMockContext.() -> R): StubbingContext<R> {
        val recording = record(block)
        if(recording.isEmpty()) throw MMockStubbingException("""No methods recorded in "every" block""")
        if(recording.size > 1) throw MMockStubbingException("""Multiple invocations in "every" block""")

        return StubbingContext(recording[0])
    }

    @MMockDSL
    suspend fun verify(block: suspend VerificationContext.() -> Unit) {
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
        val objectMock = requireNotNull(invocation.objectMock)
        val name = requireNotNull(invocation.name)

        objectMock.mocks.regular[name].add(FunctionMock(invocation.args, value))
    }
}
