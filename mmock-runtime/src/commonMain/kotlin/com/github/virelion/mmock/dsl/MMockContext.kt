package com.github.virelion.mmock.dsl

import com.github.virelion.mmock.MMockStubbingException
import com.github.virelion.mmock.RecordingDoneMarker
import com.github.virelion.mmock.backend.FunctionMock
import com.github.virelion.mmock.backend.RecordLog
import com.github.virelion.mmock.backend.RecordingContextImpl
import com.github.virelion.mmock.backend.StubbingContext
import com.github.virelion.mmock.backend.ThrowingFunctionMock

class MMockContext {
    internal val recording = RecordLog()
    internal val recordingContext = RecordingContextImpl()

    @MMockDSL
    val mock = MockInitializer(this)

    @MMockDSL
    suspend fun <R> every(block: suspend RecordingContext.() -> R): StubbingContext<R> {
        val recording = recordingContext.record(block)
        if (recording.isEmpty()) throw MMockStubbingException("""No methods recorded in "every" block""")
        if (recording.size > 1) throw MMockStubbingException("""Multiple invocations in "every" block""")

        return StubbingContext(recording[0])
    }

    @MMockDSL
    infix fun <R> StubbingContext<R>.returns(value: R) {
        val objectMock = requireNotNull(invocation.objectMock)
        val name = requireNotNull(invocation.name)

        objectMock.mocks.regular[name].add(FunctionMock(invocation.args, value))
    }

    @MMockDSL
    infix fun <R> StubbingContext<R>.throws(value: Exception) {
        val objectMock = requireNotNull(invocation.objectMock)
        val name = requireNotNull(invocation.name)

        objectMock.mocks.regular[name].add(ThrowingFunctionMock(invocation.args, value))
    }

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
