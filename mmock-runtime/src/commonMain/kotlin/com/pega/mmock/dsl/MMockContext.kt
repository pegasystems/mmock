/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.dsl

import com.pega.mmock.MMockStubbingException
import com.pega.mmock.RecordingDoneMarker
import com.pega.mmock.backend.FunctionMock
import com.pega.mmock.backend.RecordLog
import com.pega.mmock.backend.RecordingContextImpl
import com.pega.mmock.backend.StubbingContext
import com.pega.mmock.backend.ThrowingFunctionMock

class MMockContext {
    internal val recording = RecordLog()
    internal val recordingContext = RecordingContextImpl()

    @MMockDSL
    val mock = MockInitializer(this)

    /*
     * Record the invocation inside [block]. Should be followed with [returns] or [throws]
     */
    @MMockDSL
    suspend fun <R> every(block: suspend RecordingContext.() -> R): StubbingContext<R> {
        val recording = recordingContext.record(block)
        if (recording.isEmpty()) throw MMockStubbingException("""No methods recorded in "every" block""")
        if (recording.size > 1) throw MMockStubbingException("""Multiple invocations in "every" block""")

        return StubbingContext(recording[0])
    }

    /*
     * Record that [com.pega.mmock.backend.StubbingContext.invocation] should return [value].
     */
    @MMockDSL
    infix fun <R> StubbingContext<R>.returns(value: R) {
        val objectMock = requireNotNull(invocation.objectMock)
        val name = requireNotNull(invocation.name)

        objectMock.mocks.regular[name].add(FunctionMock(invocation.args, value))
    }

    /*
     * Record that [com.pega.mmock.backend.StubbingContext.invocation] should throw [value].
     */
    @MMockDSL
    infix fun <R> StubbingContext<R>.throws(value: Exception) {
        val objectMock = requireNotNull(invocation.objectMock)
        val name = requireNotNull(invocation.name)

        objectMock.mocks.regular[name].add(ThrowingFunctionMock(invocation.args, value))
    }

    /*
     * Verify statements inside [block].
     */
    @MMockDSL
    suspend fun verify(
        block: suspend VerificationContext.() -> Unit
    ) {
        try {
            VerificationContext(recording, recordingContext).verify(block)
        } catch (e: RecordingDoneMarker) {
            throw IllegalStateException("Mock invoked outside of `invocation { }`")
        }
    }
}
