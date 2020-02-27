package com.github.virelion.mmock.dsl

@MMockDSL
inline fun <reified T> MMockContext.any(): T {
    TODO()
}

@MMockDSL
inline fun <reified T> MMockContext.eq(item: T): T {
    return item
}

@MMockDSL
inline fun <reified T> MMockContext.instanceOf(): T {
    TODO()
}