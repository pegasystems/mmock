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
        UnsafeAndroid().allocateInstance(subclasses[0].java)
    } else if (T::class.java.isInterface) {
        Proxy.newProxyInstance(
            T::class.java.classLoader,
            arrayOf(T::class.java),
            ThrowingInvocationHandler()
        ) as T
    } else {
        UnsafeAndroid().allocateInstance(T::class.java)
    }
}
