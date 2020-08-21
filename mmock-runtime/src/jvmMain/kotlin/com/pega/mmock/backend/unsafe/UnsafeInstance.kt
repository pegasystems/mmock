/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.backend.unsafe

import com.pega.mmock.backend.exception.SealedClassNoSubclassException
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import net.sf.cglib.proxy.Enhancer
import net.sf.cglib.proxy.MethodInterceptor
import net.sf.cglib.proxy.MethodProxy
import org.objenesis.ObjenesisHelper

actual inline fun <reified T> createUnsafe(): T {
    return if (T::class.isAbstract) {
        val enhancer = Enhancer()
        val interceptor = MethodInterceptor { _: Any, _: Method, _: Array<Any>, _: MethodProxy -> }
        enhancer.setSuperclass(T::class.java)
        enhancer.setCallbackType(interceptor.javaClass)
        val proxy = enhancer.createClass()
        ObjenesisHelper.newInstance(proxy) as T
    } else if (T::class.isSealed) {
        val subclasses = T::class.sealedSubclasses
        if (subclasses.isEmpty())
            throw SealedClassNoSubclassException("${T::class.simpleName} does not have any subclass defined. It's not efficient to declare sealed class without subclasses.")
        JVMUnsafe.unsafe.allocateInstance(subclasses[0].java) as T
    } else if (T::class.java.isInterface) {
        Proxy.newProxyInstance(
            T::class.java.classLoader,
            arrayOf(T::class.java),
            ThrowingInvocationHandler()
        ) as T
    } else {
        JVMUnsafe.createUnsafeIntsanceOfType()
    }
}
