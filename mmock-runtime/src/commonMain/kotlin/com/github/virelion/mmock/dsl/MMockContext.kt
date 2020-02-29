package com.github.virelion.mmock.dsl

import com.github.virelion.mmock.backend.FunctionMock
import com.github.virelion.mmock.backend.StubbingContext
import com.github.virelion.mmock.backend.stack.Invocation
import com.github.virelion.mmock.backend.stack.StackElement

class MMockContext {
    enum class State {
        RECORDING, INVOKING
    }

    var state: State = State.INVOKING
        private set

    var eventStack: MutableList<StackElement>? = null

    fun <R> record(block: MMockContext.() -> R): Invocation<R> {
        try {
            state = State.RECORDING
            val finalEventStack = mutableListOf<StackElement>()
            eventStack = finalEventStack
            block()
            eventStack = null

            return finalEventStack.fold(Invocation()) { invocation, element ->
                element.visit(invocation)
                invocation
            }
        } finally {
            state = State.INVOKING
            eventStack = null
        }
    }

    @MMockDSL
    fun <R> every(block: MMockContext.() -> R): StubbingContext<R> {
        return StubbingContext(record(block))
    }

    @MMockDSL
    infix fun <R> StubbingContext<R>.returns(value: R) {
        val objectMock = requireNotNull(invocation.objectMock)
        val name = requireNotNull(invocation.name)

        objectMock.mocks.regular[name].add(FunctionMock(invocation.args) { value })
    }
}

