package com.pega.mmock.ksp.template

import com.pega.mmock.ksp.utils.CodeBuilder

internal interface CodeTemplate {
    fun generate(builder: CodeBuilder): String
}
