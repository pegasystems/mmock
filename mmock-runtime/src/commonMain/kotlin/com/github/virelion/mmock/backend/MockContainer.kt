package com.github.virelion.mmock.backend

import com.github.virelion.mmock.NoMethodStubException
import com.github.virelion.mmock.RecordingDoneMarker
import com.github.virelion.mmock.backend.stack.MethodElement
import com.github.virelion.mmock.dsl.MMockContext

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
