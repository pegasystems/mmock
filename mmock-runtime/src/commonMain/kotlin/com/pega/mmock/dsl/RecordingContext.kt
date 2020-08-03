/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.dsl

import com.pega.mmock.backend.stack.ArgumentStackElement
import com.pega.mmock.backend.stack.Invocation
import com.pega.mmock.backend.stack.StackElement
import com.pega.mmock.backend.unsafe.defaultArrayInstance
import com.pega.mmock.backend.unsafe.defaultInstance
import com.pega.mmock.backend.unsafe.defaultMutableListInstance
import com.pega.mmock.backend.unsafe.defaultMutableMapInstance
import com.pega.mmock.backend.unsafe.defaultMutableSetInstance

interface RecordingContext {
    val recordingStack: MutableList<StackElement>?

    suspend fun <R> record(block: suspend RecordingContext.() -> R): List<Invocation<R>>
}

@MMockDSL
inline fun <reified T> RecordingContext.any(): T {
    recordingStack?.add(ArgumentStackElement { it is T })
    return defaultInstance()
}

@MMockDSL
inline fun <reified T> RecordingContext.anyArray(): Array<T> {
    recordingStack?.add(ArgumentStackElement { it is Array<*> })
    return defaultArrayInstance()
}

@MMockDSL
inline fun <reified T : List<R>, reified R> RecordingContext.anyList(): T {
    recordingStack?.add(ArgumentStackElement { it is T })
    return defaultMutableListInstance<R>() as T
}

@MMockDSL
inline fun <reified T : Set<R>, reified R> RecordingContext.anySet(): T {
    recordingStack?.add(ArgumentStackElement { it is T })
    return defaultMutableSetInstance<R>() as T
}

@MMockDSL
inline fun <reified T : Map<R, E>, reified R, reified E> RecordingContext.anyMap(): T {
    recordingStack?.add(ArgumentStackElement { it is T })
    return defaultMutableMapInstance<R, E>() as T
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
