/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.backend.unsafe

import com.ironz.unsafe.UnsafeAndroid
import com.pega.mmock.backend.exception.SealedClassNoSubclassException
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import net.sf.cglib.proxy.Enhancer
import net.sf.cglib.proxy.MethodInterceptor
import net.sf.cglib.proxy.MethodProxy
import org.objenesis.ObjenesisHelper

actual inline fun <reified T> createUnsafe(): T {
    return when {
        T::class.isSealed -> createUnsafeSealed()
        T::class.isAbstract -> createUnsafeAbstract()
        T::class.java.isInterface -> createUnsafeInterface()
        else -> UnsafeAndroid().allocateInstance(T::class.java)
    }
}

/*
 * Abstract class instance created using cglib and objenesis. Instance is created without using constructor.
 */
inline fun <reified T> createUnsafeAbstract(): T {
    val enhancer = Enhancer()
    val interceptor = MethodInterceptor { _: Any, _: Method, _: Array<Any>, _: MethodProxy -> }
    enhancer.setSuperclass(T::class.java)
    enhancer.setCallbackType(interceptor.javaClass)
    val proxy = enhancer.createClass()
    return ObjenesisHelper.newInstance(proxy) as T
}

/*
 * Cglib and objenesis cannot be used to create instance of sealed class.
 * Instance of subclass created instead (throw exception if there is no subclass - known issue).
 */
inline fun <reified T> createUnsafeSealed(): T {
    val subclasses = T::class.sealedSubclasses
    if (subclasses.isEmpty())
        throw SealedClassNoSubclassException("${T::class.simpleName} does not have any subclass defined. It's not efficient to declare sealed class without subclasses.")
    return UnsafeAndroid().allocateInstance(subclasses[0].java)
}

/*
 * Using interface proxy to create an instance of the interface.
 */
inline fun <reified T> createUnsafeInterface(): T {
    return Proxy.newProxyInstance(
        T::class.java.classLoader,
        arrayOf(T::class.java),
        ThrowingInvocationHandler()
    ) as T
}
