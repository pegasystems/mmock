package com.pega.mmock.compiler.codegen

data class ConstructorParameterCodeTemplate(
    val name: String,
    val type: String,
    val hasDefaultValue: Boolean
) {
    override fun toString(): String {
        return "override val $name: $type"
    }
}
