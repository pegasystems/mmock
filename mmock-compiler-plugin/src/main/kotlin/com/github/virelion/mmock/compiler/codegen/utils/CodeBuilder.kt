package com.github.virelion.mmock.compiler.codegen.utils

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

    fun appendln(line: String = "") {
        builder.appendln(indentationString + line)
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
