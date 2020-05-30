package com.github.virelion.mmock.backend

import com.github.virelion.mmock.backend.stack.Invocation

class InvocationLogRecord(
    val objectMock: ObjectMock,
    val methodName: String,
    val args: Array<out Any?>
) {
    fun fits(invocation: Invocation<*>): Boolean {
        return invocation.objectMock === objectMock &&
                invocation.name == methodName &&
                invocation.args.verify(args)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as InvocationLogRecord

        if (objectMock !== other.objectMock) return false
        if (methodName != other.methodName) return false
        if (!args.contentEquals(other.args)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = objectMock.hashCode()
        result = 31 * result + methodName.hashCode()
        result = 31 * result + args.contentHashCode()
        return result
    }
}
