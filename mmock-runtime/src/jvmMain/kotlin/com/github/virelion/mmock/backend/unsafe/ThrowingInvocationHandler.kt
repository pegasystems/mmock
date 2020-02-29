package com.github.virelion.mmock.backend.unsafe

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

class ThrowingInvocationHandler: InvocationHandler {
    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any {
        throw IllegalAccessException("Invoked unsafe instance")
    }
}