/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.dsl

import com.pega.mmock.backend.stack.ArgumentStackElement
import com.pega.mmock.backend.stack.Invocation
import com.pega.mmock.backend.stack.StackElement
import com.pega.mmock.backend.unsafe.defaultInstance

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
