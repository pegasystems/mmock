package com.github.virelion.mmock.compiler.codegen

import com.github.virelion.mmock.compiler.codegen.utils.CodeBuilder

internal class PropertyTemplate(
        val name: String,
        val type: String,
        val mutable: Boolean
) : CodeTemplate {
    private val tag: String = if(mutable) "var" else "val"

    override fun generate(builder: CodeBuilder): String {
        return builder.build {
            appendln("override $tag $name: $type")
            indent {
                appendln("get() { return mocks.invoke(\"`property`(GET, $name)\") }")
                if(mutable) {
                    appendln("set(value) { return mocks.invoke(\"`property`(SET, $name)\", value) }")
                }
            }
        }
    }
}