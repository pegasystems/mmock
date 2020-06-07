package com.github.virelion.mmock.backend

class ThrowingFunctionMock(
    verificationFunction: ArgumentsConstraints,
    private val exception: Exception
) : FunctionMockBase<Nothing>(verificationFunction) {
    override fun invoke(): Nothing {
        throw exception
    }
}
