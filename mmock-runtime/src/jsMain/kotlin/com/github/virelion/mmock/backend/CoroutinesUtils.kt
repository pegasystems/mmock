package com.github.virelion.mmock.backend

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise

actual fun runSuspend(block: suspend () -> Unit): dynamic {
    return GlobalScope.promise {
        block()
    }
}
