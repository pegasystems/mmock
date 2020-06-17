/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.compiler.codegen.ir

import org.jetbrains.kotlin.ir.types.IrSimpleType
import org.jetbrains.kotlin.ir.types.IrStarProjection
import org.jetbrains.kotlin.ir.types.IrType
import org.jetbrains.kotlin.ir.types.IrTypeAbbreviation
import org.jetbrains.kotlin.ir.types.IrTypeArgument
import org.jetbrains.kotlin.ir.types.IrTypeProjection
import org.jetbrains.kotlin.ir.types.classOrNull
import org.jetbrains.kotlin.resolve.descriptorUtil.fqNameSafe

fun IrType.generateCode(): String {
    return when (this) {
        is IrSimpleType -> this.generateCode()
        else -> TODO()
    }
}

fun IrSimpleType.generateCode(): String {
    val fqName = this.classOrNull?.descriptor?.fqNameSafe?.asString() ?: this.classifier.descriptor.name.asString()
    val typeArguments = this.arguments.generateCode()
    val nullableMarker = if (this.hasQuestionMark) {
        "?"
    } else {
        ""
    }
    return fqName + typeArguments + nullableMarker
}

fun Collection<IrTypeArgument>.generateCode(): String {
    return if (!this.isEmpty()) {
        this.joinToString(", ", prefix = "<", postfix = ">") { it.generateCode() }
    } else {
        ""
    }
}

fun IrTypeArgument.generateCode(): String {
    return when (this) {
        is IrStarProjection -> "*"
        is IrTypeProjection -> this.type.generateCode()
        is IrTypeAbbreviation -> TODO()
        else -> TODO()
    }
}
