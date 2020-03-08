package com.github.virelion.mmock.backend

class InvocationLogRecord(
        val objectMock: ObjectMock,
        val methodName: String,
        val args: Array<out Any?>
) {
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