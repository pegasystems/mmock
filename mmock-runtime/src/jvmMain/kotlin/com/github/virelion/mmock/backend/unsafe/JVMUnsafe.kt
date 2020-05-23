package com.github.virelion.mmock.backend.unsafe

import java.lang.reflect.Field
import sun.misc.Unsafe

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
