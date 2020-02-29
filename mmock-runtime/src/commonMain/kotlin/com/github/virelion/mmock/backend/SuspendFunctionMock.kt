package com.github.virelion.mmock.backend

class SuspendFunctionMock<R>(
        val verificationFunction: ArgumentsConstraints,
        val body: suspend (Array<out Any?>) -> R
) {
    suspend operator fun invoke(vararg args: Any?): R {
        return body(args)
    }
}