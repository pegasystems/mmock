package com.github.virelion.mmock.compiler.codegen

import kotlin.test.assertEquals

fun assertCodeEquals(expected: String, actual: String) {
    assertEquals(expected.trimIndent().trim(),actual.trim().replace("\r\n", "\n"))
}