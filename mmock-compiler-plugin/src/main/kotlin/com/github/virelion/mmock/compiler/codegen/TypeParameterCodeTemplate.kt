package com.github.virelion.mmock.compiler.codegen

class TypeParameterCodeTemplate(
    val name: String,
    val variance: String
) {
    override fun toString(): String {
        return (variance + " " + name).trim()
    }
}
