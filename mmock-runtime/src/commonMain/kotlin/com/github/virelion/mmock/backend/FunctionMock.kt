package com.github.virelion.mmock.backend

class FunctionMock<R>(
    val verificationFunction: ArgumentsConstraints,
    private val result: R
) {
    operator fun invoke(): R {
        return result
    }
}
