package com.github.virelion.mmock.dsl

import com.github.virelion.mmock.backend.stack.ArgumentStackElement
import com.github.virelion.mmock.backend.unsafe.defaultInstance

@MMockDSL
inline fun <reified T> MMockContext.any(): T {
    recordingStack?.add(ArgumentStackElement { true })
    return defaultInstance()
}

@MMockDSL
inline fun <reified T> MMockContext.eq(item: T): T {
    recordingStack?.add(ArgumentStackElement { it == item })
    return item
}

@MMockDSL
inline fun <reified T> MMockContext.instanceOf(): T {
    recordingStack?.add(ArgumentStackElement { it is T })
    return defaultInstance()
}