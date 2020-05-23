package com.github.virelion.mmock.compiler.codegen.jvm

import com.github.virelion.mmock.compiler.codegen.PropertyTemplate
import org.jetbrains.kotlin.descriptors.PropertyDescriptor

internal fun PropertyDescriptor.toCodeTemplate(): PropertyTemplate {
    return PropertyTemplate(
            name = this.name.asString(),
            type = this.type.generateCode(),
            mutable = this.isVar
    )
}