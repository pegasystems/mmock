package com.github.virelion.mmock.backend.unsafe

actual inline fun <reified T> createUnsafe(): T {
    return js("undefined") as T
}