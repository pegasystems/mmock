package com.github.virelion.mmock.backend

import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object InvocationRecorder {
    fun <R> getLastInvocation(block: () -> R): Invocation<R> {
        TODO()
    }
}