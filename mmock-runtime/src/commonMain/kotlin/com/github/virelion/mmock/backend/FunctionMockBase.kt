package com.github.virelion.mmock.backend

abstract class FunctionMockBase<R>(val verificationFunction: ArgumentsConstraints) {
    abstract operator fun invoke(): R
}
