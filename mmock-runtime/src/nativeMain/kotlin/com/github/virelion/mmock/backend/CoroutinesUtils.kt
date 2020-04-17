package com.github.virelion.mmock.backend

import kotlinx.coroutines.runBlocking

actual fun runSuspend(block: suspend () -> Unit) = runBlocking {
    block()
}