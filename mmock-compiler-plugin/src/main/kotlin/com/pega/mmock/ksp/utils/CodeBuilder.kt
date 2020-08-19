/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.ksp.utils

internal class CodeBuilder(private val indentationBase: String = "    ") {
    val builder = StringBuilder()
    private var indentationString = ""

    var indent = 0
        set(value) {
            field = value

            indentationString = StringBuilder().apply {
                repeat(value) {
                    this.append(indentationBase)
                }
            }.toString()
        }

    fun appendln(line: String) {
        if (line.isNotBlank()) {
            builder.appendLine(indentationString + line.trim())
        }
    }

    fun emptyLine() {
        builder.appendLine()
    }

    fun lineOf(vararg codePart: String) {
        appendln(
                codePart.filter { it.isNotEmpty() }
                        .joinToString(separator = " ")
        )
    }

    fun indent(block: CodeBuilder.() -> Unit) {
        indent++
        block()
        indent--
    }

    fun build(block: CodeBuilder.() -> Unit): String {
        block()
        return toString()
    }

    override fun toString(): String {
        return builder.toString()
    }
}
