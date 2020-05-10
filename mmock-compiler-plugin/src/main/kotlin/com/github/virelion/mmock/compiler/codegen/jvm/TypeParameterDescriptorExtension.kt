package com.github.virelion.mmock.compiler.codegen.jvm

import com.github.virelion.mmock.compiler.codegen.TypeParameterCodeTemplate
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor

fun TypeParameterDescriptor.toCodeString(): TypeParameterCodeTemplate {
    return TypeParameterCodeTemplate(name.asString(), variance.label)
}