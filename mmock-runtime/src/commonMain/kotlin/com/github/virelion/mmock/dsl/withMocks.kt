package com.github.virelion.mmock.dsl

import com.github.virelion.mmock.backend.runSuspend

fun withMMock(block: suspend MMockContext.() -> Unit) {
    return runSuspend {
        MMockContext().apply {
            try {
                block()
            } finally {
                cleanup()
            }
        }
    }
}