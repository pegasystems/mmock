package com.github.virelion.mmock.compiler.codegen

import com.github.virelion.mmock.compiler.codegen.utils.CodeBuilder

internal interface CodeTemplate {
    fun generate(builder: CodeBuilder): String
}
