/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.backend

import com.pega.mmock.NoMethodStubException
import com.pega.mmock.RecordingDoneMarker
import com.pega.mmock.backend.stack.MethodElement
import com.pega.mmock.dsl.MMockContext

class MockContainer(
    val objectMock: ObjectMock
) {
    val context: MMockContext
        get() = objectMock.mMockContext

    val regular: FunctionRegistry<FunctionMockBase<*>> = FunctionRegistry()

    fun <T> invoke(name: String, vararg args: Any? = arrayOf()): T {
        when (context.recordingContext.state) {
            RecordingContextImpl.State.RECORDING -> {
                context.recordingContext.recordingStack?.add(MethodElement(name, objectMock, args))
                throw RecordingDoneMarker()
            }
            RecordingContextImpl.State.INVOKING -> {
                val functionMock = regular[name]
                        .firstOrNull { it.verificationFunction.verify(args) }
                        ?: throw NoMethodStubException()

                context.recording.log.add(
                        InvocationLogRecord(
                                objectMock = objectMock,
                                methodName = name,
                                args = args
                        )
                )

                @Suppress("unchecked_cast")
                return functionMock.invoke() as T
            }
        }
    }
}
