package com.github.virelion.mmock.compiler.codegen.jvm

import com.github.virelion.mmock.compiler.Annotations
import org.jetbrains.kotlin.js.descriptorUtils.getJetTypeFqName
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.types.KotlinType

fun KotlinType?.generateCode(): String {
    requireNotNull(this)

    val typeParametrAnnotation = annotations.findAnnotation(Annotations.TYPE_PARAMETER)

    if (typeParametrAnnotation != null) {
        return typeParametrAnnotation.allValueArguments[Name.identifier("name")]
                ?.value as? String
                ?: throw IllegalStateException("TypeParameter with empty name")
    }

    val fqString = getJetTypeFqName(false)
    val typeArguments = if (arguments.isNotEmpty()) {
        arguments.joinToString(separator = ", ", prefix = "<", postfix = ">") { it.type.generateCode() }
    } else {
        ""
    }

    val nullableIndicator = if (isMarkedNullable) "?" else ""

    return fqString + typeArguments + nullableIndicator
}
