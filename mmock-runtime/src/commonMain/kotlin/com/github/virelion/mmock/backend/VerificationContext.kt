package com.github.virelion.mmock.backend

class VerificationContext<ReturnType>(
        val invocation: Invocation<ReturnType>,
        val argumentsConstraints: Array<ArgumentsVerificationFunction>?,
        val invocationAmountConstraint: InvocationAmountConstraint? = { it > 0 }
)