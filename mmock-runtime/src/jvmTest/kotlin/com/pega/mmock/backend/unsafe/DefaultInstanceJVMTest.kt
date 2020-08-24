/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.backend.unsafe

import com.pega.mmock.backend.exception.SealedClassNoSubclassException
import com.pega.mmock.backend.runSuspend
import kotlin.test.assertFailsWith
import kotlinx.coroutines.delay
import org.junit.Test

@OptIn(ExperimentalUnsignedTypes::class)
class DefaultInstanceJVMTest {
    private sealed class A(val arg1: String, val arg2: Int)

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
    fun `Creating default instance of sealed class without subclass fails`() {
        assertFailsWith<SealedClassNoSubclassException> { defaultInstance<A>() }
        assertFailsWith<SealedClassNoSubclassException> { defaultInstance<A?>() }
    }

    @Test
    fun `Method with sealed class without subclass as argument fails`() {
        assertFailsWith<SealedClassNoSubclassException> { regularFunction(defaultInstance<A>()) }
        assertFailsWith<SealedClassNoSubclassException> { regularFunctionWithNullableArgument(defaultInstance<A?>()) }
    }

    @Test
    fun `Suspend method with sealed class without subclass as argument fails`() = runSuspend {
        assertFailsWith<SealedClassNoSubclassException> { suspendFunction(defaultInstance<A>()) }
        assertFailsWith<SealedClassNoSubclassException> { suspendFunctionWithNullableArgument(defaultInstance<A?>()) }
    }
}
