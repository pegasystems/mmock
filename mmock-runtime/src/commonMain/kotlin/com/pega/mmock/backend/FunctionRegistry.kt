/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.backend

class FunctionRegistry<T> {
    private val map = mutableMapOf<String, MutableList<T>>()

    operator fun get(key: String): MutableList<T> {
        if (key !in map) {
            map[key] = mutableListOf()
        }
        return map[key] ?: throw IllegalStateException()
    }
}
