/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.compiler.codegen

import kotlin.test.assertEquals

fun assertCodeEquals(expected: String, actual: String) {
    assertEquals(expected.trimIndent().trim(), actual.trim().replace("\r\n", "\n"))
}
