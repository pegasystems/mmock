package com.github.virelion.mmock.dsl

import com.github.virelion.mmock.backend.StubbingContext
import com.github.virelion.mmock.backend.VerificationContext

class MMockContext {
    @MMockDSL
    fun <R> every(block: () -> R): StubbingContext<R> {
        TODO()
    }

    @MMockDSL
    infix fun <R> StubbingContext<R>.returns(value: R) {

    }

    @MMockDSL
    fun <R> verify(order: Order = Order.ANY, block: MMockContext.() -> R): VerificationContext<R> {
        TODO()
    }

    @MMockDSL
    infix fun Any?.called(matcher: InvocationMatcher) {

    }

    fun cleanup() {

    }
}

