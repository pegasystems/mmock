package ksp.template

import ksp.utils.CodeBuilder

internal interface CodeTemplate {
    fun generate(builder: CodeBuilder): String
}
