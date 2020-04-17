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
import com.github.virelion.mmock.samples.ExampleInterface
import com.github.virelion.mmock.samples.ExampleMock

class MMockContext: VerificationContext {
    enum class State {
        RECORDING, INVOKING
    }

    var state: State = State.INVOKING
        private set

    @PublishedApi
    internal val invocationLogRecord: MutableList<InvocationLogRecord> = mutableListOf()

    override var recordingStack: MutableList<StackElement>? = null

    private suspend fun <R> record(block:  suspend MMockContext.() -> R): List<Invocation<R>> {
        try {
            state = State.RECORDING
            val finalEventStack = mutableListOf<StackElement>()
            recordingStack = finalEventStack
            println("PRE - block")
            try {
                block()
            } catch (e: RecordingDoneMarker) {

            }
            println("POST - block")
            recordingStack = null

            val invocations = mutableListOf<Invocation<R>>()
            var invocation = Invocation<R>()

            println("PRE - stack analysis")
            finalEventStack.forEach {
                it.visit(invocation, invocations)
                if(it is MethodElement) {
                    invocations.add(invocation)
                    invocation = Invocation()
                }
            }
            println("POST - stack analysis")

            println("PRE - return")
            return invocations
        } finally {
            state = State.INVOKING
            recordingStack = null
            println("FINALLY")
        }
    }

    @MMockDSL
    suspend fun <R> every(block: suspend MMockContext.() -> R): StubbingContext<R> {
        println("PRE - every")
        val recording = record(block)
        if(recording.isEmpty()) throw MMockStubbingException("""No methods recorded in "every" block""")
        if(recording.size > 1) throw MMockStubbingException("""Multiple invocations in "every" block""")

        println("PRE - every return")
        return StubbingContext(recording[0])
    }

    @MMockDSL
    suspend fun verify(block: suspend VerificationContext.() -> Unit) {
        println("PRE - verify")
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
        println("POST - verify")
    }

    @MMockDSL
    infix fun <R> StubbingContext<R>.returns(value: R) {
        val objectMock = requireNotNull(invocation.objectMock)
        val name = requireNotNull(invocation.name)

        objectMock.mocks.regular[name].add(FunctionMock(invocation.args, value))
    }
}

@MMockDSL
inline fun <reified T> MMockContext.mmock(): T {
    return when (T::class) {
        ExampleInterface::class -> ExampleMock(this)
        else -> throw IllegalArgumentException("ExampleInterface only")
    } as T
}