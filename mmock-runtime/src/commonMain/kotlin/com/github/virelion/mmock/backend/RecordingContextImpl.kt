package com.github.virelion.mmock.backend

import com.github.virelion.mmock.RecordingDoneMarker
import com.github.virelion.mmock.backend.stack.Invocation
import com.github.virelion.mmock.backend.stack.MethodElement
import com.github.virelion.mmock.backend.stack.StackElement
import com.github.virelion.mmock.dsl.RecordingContext

internal class RecordingContextImpl : RecordingContext {
    enum class State {
        RECORDING, INVOKING
    }

    var state: State = State.INVOKING
        private set

    override var recordingStack: MutableList<StackElement>? = null

    override suspend fun <R> record(block: suspend RecordingContext.() -> R): List<Invocation<R>> {
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
                if (it is MethodElement) {
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
}
