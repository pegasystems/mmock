package com.github.virelion.mmock.backend.stack

import com.github.virelion.mmock.backend.ArgumentsVerificationFunction
import com.github.virelion.mmock.backend.ObjectMock

class Invocation<ReturnType> {
    var args: MutableList<ArgumentsVerificationFunction> = mutableListOf()
    var objectMock: ObjectMock? = null
    var name: String? = null
}