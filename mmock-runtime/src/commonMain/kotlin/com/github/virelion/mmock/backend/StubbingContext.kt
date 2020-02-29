package com.github.virelion.mmock.backend

import com.github.virelion.mmock.backend.stack.Invocation

class StubbingContext<ReturnType>(
        val invocation: Invocation<ReturnType>
)