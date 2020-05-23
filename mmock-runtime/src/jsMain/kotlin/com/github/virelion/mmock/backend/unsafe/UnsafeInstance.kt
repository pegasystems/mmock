package com.github.virelion.mmock.backend.unsafe

actual inline fun <reified T> createUnsafe(): T {
    // This should not be implicitly cast
    // Implicit cast will always fail
    return js("undefined")
}
