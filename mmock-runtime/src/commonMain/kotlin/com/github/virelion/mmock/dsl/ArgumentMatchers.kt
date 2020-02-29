package com.github.virelion.mmock.dsl

import com.github.virelion.mmock.backend.stack.ArgumentStackElement
import com.github.virelion.mmock.backend.unsafe.createUnsafe

@MMockDSL
inline fun <reified T> MMockContext.any(): T {
    eventStack?.add(ArgumentStackElement { true })
    return createUnsafe()
}

@MMockDSL
inline fun <reified T> MMockContext.eq(item: T): T {
    eventStack?.add(ArgumentStackElement { it == item })
    return item
}

@MMockDSL
inline fun <reified T> MMockContext.instanceOf(): T {
    eventStack?.add(ArgumentStackElement { it is T })
    return createUnsafe()
}