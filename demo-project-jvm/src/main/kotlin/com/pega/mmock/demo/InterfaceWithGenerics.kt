/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.demo

import com.pega.mmock.GenerateMock

// @GenerateMock
interface InterfaceWithGenerics<in T, out R, V> {
    val property: R
    var mutableProperty: V

    fun function(input: T): R
}
