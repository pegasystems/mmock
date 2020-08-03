/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.backend.unsafe

import com.pega.mmock.backend.runSuspend
import kotlin.js.JsName
import kotlin.test.Test
import kotlinx.coroutines.delay

@OptIn(ExperimentalUnsignedTypes::class)
class DefaultInstanceTest {
    private interface A
    private class B

    @Suppress("unused_parameter")
    fun regularFunction(arg: Any) {
        // empty body
    }

    @Suppress("unused_parameter")
    fun regularFunctionWithNullableArgument(arg: Any?) {
        // empty body
    }

    @Suppress("unused_parameter")
    suspend fun suspendFunction(arg: Any) {
        delay(1)
    }

    @Suppress("unused_parameter")
    suspend fun suspendFunctionWithNullableArgument(arg: Any?) {
        delay(1)
    }

    @Test
    @JsName("Can_be_created")
    fun `Can be created`() {
        defaultInstance<Boolean>()
        defaultInstance<Int>()
        defaultInstance<Short>()
        defaultInstance<Long>()
        defaultInstance<Byte>()
        defaultInstance<UInt>()
        defaultInstance<UShort>()
        defaultInstance<ULong>()
        defaultInstance<UByte>()
        defaultInstance<Double>()
        defaultInstance<Char>()
        defaultInstance<String>()
        defaultInstance<A>()
        defaultInstance<B>()
    }

    @Test
    @JsName("Can_be_created_primitive_arrays_and_collections")
    fun `Can be created primitive arrays and collections`() {
        defaultInstance<IntArray>()
        defaultInstance<BooleanArray>()
        defaultInstance<FloatArray>()
        defaultInstance<ShortArray>()
        defaultInstance<LongArray>()
        defaultInstance<ByteArray>()
        defaultInstance<DoubleArray>()
        defaultInstance<CharArray>()
        defaultInstance<UByteArray>()
        defaultInstance<UShortArray>()
        defaultInstance<UIntArray>()
        defaultInstance<ULongArray>()
        defaultArrayInstance<A>()
        defaultMapInstance<A, B>()
        defaultListInstance<Int>()
        defaultSetInstance<A>()
        // defaultMutableListInstance()
        defaultMutableMapInstance<Float, Char>()
        defaultMutableSetInstance<B>()
    }

    @Test
    @JsName("Can_be_passed_into_function")
    fun `Can be passed into function`() {
        regularFunction(defaultInstance<Boolean>())
        regularFunction(defaultInstance<Int>())
        regularFunction(defaultInstance<Short>())
        regularFunction(defaultInstance<Long>())
        regularFunction(defaultInstance<Byte>())
        regularFunction(defaultInstance<UInt>())
        regularFunction(defaultInstance<UShort>())
        regularFunction(defaultInstance<ULong>())
        regularFunction(defaultInstance<UByte>())
        regularFunction(defaultInstance<Double>())
        regularFunction(defaultInstance<Char>())
        regularFunction(defaultInstance<String>())
        regularFunction(defaultInstance<List<Int>>())
        regularFunction(defaultInstance<A>())
        regularFunction(defaultInstance<B>())
        regularFunction(defaultInstance<IntArray>())
        regularFunction(defaultInstance<BooleanArray>())
        regularFunction(defaultInstance<FloatArray>())
        regularFunction(defaultInstance<ShortArray>())
        regularFunction(defaultInstance<LongArray>())
        regularFunction(defaultInstance<ByteArray>())
        regularFunction(defaultInstance<DoubleArray>())
        regularFunction(defaultInstance<CharArray>())
        regularFunction(defaultInstance<UByteArray>())
        regularFunction(defaultInstance<UShortArray>())
        regularFunction(defaultInstance<UIntArray>())
        regularFunction(defaultInstance<ULongArray>())
        regularFunction(defaultArrayInstance<A>())
        regularFunction(defaultMapInstance<A, B>())
        regularFunction(defaultListInstance<Int>())
        regularFunction(defaultSetInstance<A>())
        // regularFunction(defaultMutableListInstance())
        regularFunction(defaultMutableMapInstance<Float, Char>())
        regularFunction(defaultMutableSetInstance<B>())
    }

    @Test
    @JsName("Can_be_passed_into_suspend_function")
    fun `Can be passed into suspend function`() = runSuspend {
        suspendFunction(defaultInstance<Boolean>())
        suspendFunction(defaultInstance<Int>())
        suspendFunction(defaultInstance<Short>())
        suspendFunction(defaultInstance<Long>())
        suspendFunction(defaultInstance<Byte>())
        suspendFunction(defaultInstance<UInt>())
        suspendFunction(defaultInstance<UShort>())
        suspendFunction(defaultInstance<ULong>())
        suspendFunction(defaultInstance<UByte>())
        suspendFunction(defaultInstance<Double>())
        suspendFunction(defaultInstance<Char>())
        suspendFunction(defaultInstance<String>())
        suspendFunction(defaultInstance<List<Int>>())
        suspendFunction(defaultInstance<A>())
        suspendFunction(defaultInstance<B>())
        suspendFunction(defaultInstance<IntArray>())
        suspendFunction(defaultInstance<BooleanArray>())
        suspendFunction(defaultInstance<FloatArray>())
        suspendFunction(defaultInstance<ShortArray>())
        suspendFunction(defaultInstance<LongArray>())
        suspendFunction(defaultInstance<ByteArray>())
        suspendFunction(defaultInstance<DoubleArray>())
        suspendFunction(defaultInstance<CharArray>())
        suspendFunction(defaultInstance<UByteArray>())
        suspendFunction(defaultInstance<UShortArray>())
        suspendFunction(defaultInstance<UIntArray>())
        suspendFunction(defaultInstance<ULongArray>())
        suspendFunction(defaultArrayInstance<A>())
        suspendFunction(defaultMapInstance<A, B>())
        suspendFunction(defaultListInstance<Int>())
        suspendFunction(defaultSetInstance<A>())
        // suspendFunction(defaultMutableListInstance())
        suspendFunction(defaultMutableMapInstance<Float, Char>())
        suspendFunction(defaultMutableSetInstance<B>())
    }

    @Test
    @JsName("Can_be_created_nullable")
    fun `Can be created nullable`() {
        defaultInstance<Boolean?>()
        defaultInstance<Int?>()
        defaultInstance<Short?>()
        defaultInstance<Long?>()
        defaultInstance<Byte?>()
        defaultInstance<UInt?>()
        defaultInstance<UShort?>()
        defaultInstance<ULong?>()
        defaultInstance<UByte?>()
        defaultInstance<Double?>()
        defaultInstance<Char?>()
        defaultInstance<String?>()
        defaultInstance<A?>()
        defaultInstance<B?>()
    }

    @Test
    @JsName("Can_be_created_primitive_arrays_and_collections_nullable")
    fun `Can be created primitive arrays and collections nullable`() {
        defaultInstance<IntArray?>()
        defaultInstance<BooleanArray?>()
        defaultInstance<FloatArray?>()
        defaultInstance<ShortArray?>()
        defaultInstance<LongArray?>()
        defaultInstance<ByteArray?>()
        defaultInstance<DoubleArray?>()
        defaultInstance<CharArray?>()
        defaultInstance<UByteArray?>()
        defaultInstance<UShortArray?>()
        defaultInstance<UIntArray?>()
        defaultInstance<ULongArray?>()
        defaultInstance<List<Int>>()
        defaultInstance<Set<String>>()
        defaultInstance<Map<Long, B>>()
        defaultArrayInstance<A?>()
        defaultMapInstance<A?, B?>()
        defaultListInstance<Int?>()
        defaultSetInstance<A?>()
        // defaultMutableListInstance()
        defaultMutableMapInstance<Float?, Char?>()
        defaultMutableSetInstance<B?>()
    }

    @Test
    @JsName("Can_be_passed_into_suspend_function_nullable")
    fun `Can be passed into suspend function nullable`() = runSuspend {
        suspendFunctionWithNullableArgument(defaultInstance<Boolean?>())
        suspendFunctionWithNullableArgument(defaultInstance<Int?>())
        suspendFunctionWithNullableArgument(defaultInstance<Short?>())
        suspendFunctionWithNullableArgument(defaultInstance<Long?>())
        suspendFunctionWithNullableArgument(defaultInstance<Byte?>())
        suspendFunctionWithNullableArgument(defaultInstance<UInt?>())
        suspendFunctionWithNullableArgument(defaultInstance<UShort?>())
        suspendFunctionWithNullableArgument(defaultInstance<ULong?>())
        suspendFunctionWithNullableArgument(defaultInstance<UByte?>())
        suspendFunctionWithNullableArgument(defaultInstance<Double?>())
        suspendFunctionWithNullableArgument(defaultInstance<Char?>())
        suspendFunctionWithNullableArgument(defaultInstance<String?>())
        suspendFunctionWithNullableArgument(defaultInstance<A?>())
        suspendFunctionWithNullableArgument(defaultInstance<B?>())
        suspendFunctionWithNullableArgument(defaultInstance<IntArray?>())
        suspendFunctionWithNullableArgument(defaultInstance<BooleanArray?>())
        suspendFunctionWithNullableArgument(defaultInstance<FloatArray?>())
        suspendFunctionWithNullableArgument(defaultInstance<ShortArray?>())
        suspendFunctionWithNullableArgument(defaultInstance<LongArray?>())
        suspendFunctionWithNullableArgument(defaultInstance<ByteArray?>())
        suspendFunctionWithNullableArgument(defaultInstance<DoubleArray?>())
        suspendFunctionWithNullableArgument(defaultInstance<CharArray?>())
        suspendFunctionWithNullableArgument(defaultInstance<UByteArray?>())
        suspendFunctionWithNullableArgument(defaultInstance<UShortArray?>())
        suspendFunctionWithNullableArgument(defaultInstance<UIntArray?>())
        suspendFunctionWithNullableArgument(defaultInstance<ULongArray?>())
    }

    @Test
    @JsName("Can_be_passed_into_regular_function_nullable")
    fun `Can be passed into regular function nullable`() {
        regularFunctionWithNullableArgument(defaultInstance<Boolean?>())
        regularFunctionWithNullableArgument(defaultInstance<Int?>())
        regularFunctionWithNullableArgument(defaultInstance<Short?>())
        regularFunctionWithNullableArgument(defaultInstance<Long?>())
        regularFunctionWithNullableArgument(defaultInstance<Byte?>())
        regularFunctionWithNullableArgument(defaultInstance<UInt?>())
        regularFunctionWithNullableArgument(defaultInstance<UShort?>())
        regularFunctionWithNullableArgument(defaultInstance<ULong?>())
        regularFunctionWithNullableArgument(defaultInstance<UByte?>())
        regularFunctionWithNullableArgument(defaultInstance<Double?>())
        regularFunctionWithNullableArgument(defaultInstance<Char?>())
        regularFunctionWithNullableArgument(defaultInstance<String?>())
        regularFunctionWithNullableArgument(defaultInstance<A?>())
        regularFunctionWithNullableArgument(defaultInstance<B?>())
        regularFunctionWithNullableArgument(defaultInstance<IntArray?>())
        regularFunctionWithNullableArgument(defaultInstance<BooleanArray?>())
        regularFunctionWithNullableArgument(defaultInstance<FloatArray?>())
        regularFunctionWithNullableArgument(defaultInstance<ShortArray?>())
        regularFunctionWithNullableArgument(defaultInstance<LongArray?>())
        regularFunctionWithNullableArgument(defaultInstance<ByteArray?>())
        regularFunctionWithNullableArgument(defaultInstance<DoubleArray?>())
        regularFunctionWithNullableArgument(defaultInstance<CharArray?>())
        regularFunctionWithNullableArgument(defaultInstance<UByteArray?>())
        regularFunctionWithNullableArgument(defaultInstance<UShortArray?>())
        regularFunctionWithNullableArgument(defaultInstance<UIntArray?>())
        regularFunctionWithNullableArgument(defaultInstance<ULongArray?>())
    }
}
