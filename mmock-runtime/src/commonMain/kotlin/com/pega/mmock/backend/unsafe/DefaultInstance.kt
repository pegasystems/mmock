/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.backend.unsafe

@OptIn(ExperimentalUnsignedTypes::class)
inline fun <reified T> defaultInstance(): T {
    return when (T::class) {
        Boolean::class -> false as T

        Int::class -> 0 as T
        Short::class -> 0.toShort() as T
        Long::class -> 0L as T
        Byte::class -> 0x0.toByte() as T

        UInt::class -> 0u as T
        UShort::class -> 0.toUShort() as T
        ULong::class -> 0.toULong() as T
        UByte::class -> 0x0u.toUByte() as T

        Double::class -> 0.0 as T
        Char::class -> '\n' as T
        String::class -> "" as T

        // Primitive arrays
        BooleanArray::class -> booleanArrayOf() as T
        IntArray::class -> intArrayOf() as T
        ShortArray::class -> shortArrayOf() as T
        LongArray::class -> longArrayOf() as T
        FloatArray::class -> floatArrayOf() as T
        ByteArray::class -> byteArrayOf() as T
        DoubleArray::class -> doubleArrayOf() as T
        CharArray::class -> charArrayOf() as T
        UIntArray::class -> uintArrayOf() as T
        UShortArray::class -> ushortArrayOf() as T
        ULongArray::class -> ulongArrayOf() as T
        UByteArray::class -> ubyteArrayOf() as T

        else -> createUnsafe()
    }
}

inline fun <reified T> defaultArrayInstance(): Array<T> = arrayOf()

inline fun <reified T> defaultListInstance(): List<T> = listOf()

inline fun <reified T> defaultMutableListInstance(): MutableList<T> = mutableListOf()

inline fun <reified T> defaultSetInstance(): Set<T> = setOf()

inline fun <reified T, reified E> defaultMapInstance(): Map<T, E> = mapOf()

inline fun <reified T, reified E> defaultMutableMapInstance(): MutableMap<T, E> = mutableMapOf()

inline fun <reified T> defaultMutableSetInstance(): MutableSet<T> = mutableSetOf()
