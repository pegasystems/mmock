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

/*
 * Allows creating custom argument matchers.
 *
 * For custom matchers with collections use [onArray], [onList], [onSet] or [onMap].
 */
@MMockDSL
inline fun <reified T> RecordingContext.on(crossinline block: (arg: T) -> Boolean): T {
    recordingStack?.add(ArgumentStackElement { block(it as T) })
    return defaultInstance() as T
}

/*
 * Allows creating custom argument matchers with Array.
 */
@Suppress("UNCHECKED_CAST")
@MMockDSL
inline fun <reified T> RecordingContext.onArray(crossinline block: (arg: Array<T>) -> Boolean): Array<T> {
    recordingStack?.add(ArgumentStackElement {
        try {
            block(it as Array<T>)
        } catch (e: ClassCastException) {
            false
        }
    })
    return defaultArrayInstance()
}

/*
 * Allows creating custom argument matchers with List.
 */
@MMockDSL
inline fun <reified T : List<R>, reified R> RecordingContext.onList(crossinline block: (arg: T) -> Boolean): T {
    recordingStack?.add(ArgumentStackElement {
        try {
            block(it as T)
        } catch (e: ClassCastException) {
            false
        }
    })
    return defaultMutableListInstance<R>() as T
}

/*
 * Allows creating custom argument matchers with Set.
 */
@MMockDSL
inline fun <reified T : Set<R>, reified R> RecordingContext.onSet(crossinline block: (arg: T) -> Boolean): T {
    recordingStack?.add(ArgumentStackElement {
        try {
            block(it as T)
        } catch (e: ClassCastException) {
            false
        }
    })
    return defaultMutableSetInstance<R>() as T
}

/*
 * Allows creating custom argument matchers with Map.
 */
@MMockDSL
inline fun <reified T : Map<R, E>, reified R, reified E> RecordingContext.onMap(crossinline block: (arg: T) -> Boolean): T {
    recordingStack?.add(ArgumentStackElement {
        try {
            block(it as T)
        } catch (e: ClassCastException) {
            false
        }
    })
    return defaultMutableMapInstance<R, E>() as T
}

/*
 * Matches anything.
 *
 * Matches any object of given type if [T] specified.
 */
@MMockDSL
inline fun <reified T> RecordingContext.any(): T {
    recordingStack?.add(ArgumentStackElement { it is T })
    return defaultInstance()
}

/*
 * Matches any Array.
 */
@MMockDSL
inline fun <reified T> RecordingContext.anyArray(): Array<T> {
    recordingStack?.add(ArgumentStackElement { it is Array<*> })
    return defaultArrayInstance()
}

/*
 * Matches any List.
 */
@MMockDSL
inline fun <reified T : List<R>, reified R> RecordingContext.anyList(): T {
    recordingStack?.add(ArgumentStackElement { it is T })
    return defaultMutableListInstance<R>() as T
}

/*
 * Matches any Set.
 */
@MMockDSL
inline fun <reified T : Set<R>, reified R> RecordingContext.anySet(): T {
    recordingStack?.add(ArgumentStackElement { it is T })
    return defaultMutableSetInstance<R>() as T
}

/*
 * Matches any Map.
 */
@MMockDSL
inline fun <reified T : Map<R, E>, reified R, reified E> RecordingContext.anyMap(): T {
    recordingStack?.add(ArgumentStackElement { it is T })
    return defaultMutableMapInstance<R, E>() as T
}

/*
 * Argument that is equal to given value.
 */
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
