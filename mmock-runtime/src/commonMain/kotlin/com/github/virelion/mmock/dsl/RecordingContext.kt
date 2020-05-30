package com.github.virelion.mmock.dsl

import com.github.virelion.mmock.backend.stack.ArgumentStackElement
import com.github.virelion.mmock.backend.stack.Invocation
import com.github.virelion.mmock.backend.stack.StackElement
import com.github.virelion.mmock.backend.unsafe.defaultInstance

interface RecordingContext {
    val recordingStack: MutableList<StackElement>?

    suspend fun <R> record(block: suspend RecordingContext.() -> R): List<Invocation<R>>
}

@MMockDSL
inline fun <reified T> RecordingContext.any(): T {
    recordingStack?.add(ArgumentStackElement { true })
    return defaultInstance()
}

@MMockDSL
inline fun <reified T> RecordingContext.eq(item: T): T {
    recordingStack?.add(ArgumentStackElement { it == item })
    return item
}

@MMockDSL
inline fun <reified T> RecordingContext.instanceOf(): T {
    recordingStack?.add(ArgumentStackElement { it is T })
    return defaultInstance()
}
