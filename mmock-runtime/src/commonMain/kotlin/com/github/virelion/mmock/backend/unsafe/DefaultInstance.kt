package com.github.virelion.mmock.backend.unsafe

@UseExperimental(ExperimentalUnsignedTypes::class)
inline fun <reified T> defaultInstance(): T {
    return when(T::class) {
        Int::class -> 0 as T
        Short::class -> 0.toShort() as T
        Long::class -> 0L as T
        Byte::class -> 0x0 as T

        UInt::class -> 0u as T
        UShort::class -> 0.toUShort() as T
        ULong::class -> 0.toULong() as T
        UByte::class -> 0x0u as T

        Double::class -> 0.0 as T
        Char::class -> "\n" as T
        String::class -> "" as T
        else -> createUnsafe()
    }
}