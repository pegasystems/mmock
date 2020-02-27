package com.github.virelion.mmock.dsl

typealias InvocationMatcher = (Int) -> Unit

fun times(amount: Int): InvocationMatcher {
    return { it == amount }
}

@MMockDSL
val once: (Int) -> Unit = times(1)

@MMockDSL
val twice: (Int) -> Unit = times(2)

@MMockDSL
val never: (Int) -> Unit = times(0)

