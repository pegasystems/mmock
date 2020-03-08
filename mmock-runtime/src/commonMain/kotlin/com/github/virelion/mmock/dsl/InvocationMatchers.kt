package com.github.virelion.mmock.dsl

typealias InvocationMatcher = (Int) -> Boolean

@MMockDSL
fun VerificationContext.times(amount: Int): InvocationMatcher {
    return { it == amount }
}

@MMockDSL
val VerificationContext.once: InvocationMatcher get() {
    return times(1)
}

@MMockDSL
val VerificationContext.twice: InvocationMatcher get() {
    return times(2)
}

@MMockDSL
val VerificationContext.never: InvocationMatcher  get() {
    return times(0)
}