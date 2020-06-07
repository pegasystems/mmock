package com.github.virelion.mmock.backend

open class FunctionMock<R>(
    verificationFunction: ArgumentsConstraints,
    private val result: R
) : FunctionMockBase<R>(verificationFunction) {
    override fun invoke(): R {
        return result
    }
}
