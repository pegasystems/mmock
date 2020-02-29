package com.github.virelion.mmock.backend.unsafe

import kotlin.native.internal.Ref

actual inline fun <reified T> createUnsafe(): T {
    return Ref<T>().element
}