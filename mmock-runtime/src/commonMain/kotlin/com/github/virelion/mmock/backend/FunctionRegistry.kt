package com.github.virelion.mmock.backend

class FunctionRegistry<T> {
    private val map = mutableMapOf<String, MutableList<T>>()

    operator fun get(key: String): MutableList<T> {
        if (key !in map) {
            map[key] = mutableListOf()
        }
        return map[key] ?: throw IllegalStateException()
    }
}
