package com.github.virelion.mmock.backend.unsafe

import sun.misc.Unsafe
import java.lang.reflect.Field


object JVMUnsafe {
    val unsafe: Unsafe

    init {
        val f: Field = Unsafe::class.java.getDeclaredField("theUnsafe")
        f.isAccessible = true
        unsafe = f.get(null) as Unsafe
    }

    inline fun <reified T> createUnsafeIntsanceOfType(): T {
        return unsafe.allocateInstance(T::class.java) as T
    }
}