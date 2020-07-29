/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.compiler.codegen.ir

import com.pega.mmock.compiler.codegen.utils.MMockCollectionEnum
import org.jetbrains.kotlin.ir.types.IrSimpleType
import org.jetbrains.kotlin.ir.types.IrStarProjection
import org.jetbrains.kotlin.ir.types.IrType
import org.jetbrains.kotlin.ir.types.IrTypeAbbreviation
import org.jetbrains.kotlin.ir.types.IrTypeArgument
import org.jetbrains.kotlin.ir.types.IrTypeProjection
import org.jetbrains.kotlin.ir.types.classOrNull
import org.jetbrains.kotlin.ir.types.classifierOrFail
import org.jetbrains.kotlin.ir.types.isPrimitiveType
import org.jetbrains.kotlin.ir.types.isString
import org.jetbrains.kotlin.resolve.descriptorUtil.fqNameSafe

fun IrType.generateCode(): String {
    return when (this) {
        is IrSimpleType -> this.generateCode()
        else -> TODO()
    }
}

fun IrType.isMmockDefaultInstanceSupported(): Boolean {
    // There is IrType#isPrimitiveArray() method but it doesn't support U-types like ULongArray
    // and seems to have unexpected behaviour with the rest of primitive arrays
    return isPrimitiveType() || isString() || isPrimitiveArrayCustom() || isCollectionInterface()
}

fun IrType.isPrimitiveArrayCustom(): Boolean {
    val fqName = this.classOrNull?.descriptor?.fqNameSafe?.asString()
        ?: this.classifierOrFail.descriptor.name.toString()
    val primitiveArrays = setOf(
        "kotlin.UIntArray",
        "kotlin.UShortArray",
        "kotlin.UByteArray",
        "kotlin.ULongArray",
        "kotlin.BooleanArray",
        "kotlin.IntArray",
        "kotlin.ShortArray",
        "kotlin.ByteArray",
        "kotlin.LongArray",
        "kotlin.DoubleArray",
        "kotlin.FloatArray",
        "kotlin.CharArray"
    )

    return fqName in primitiveArrays
}

fun IrType.isCollectionInterface(): Boolean {
    val fqName = this.classOrNull?.descriptor?.fqNameSafe?.asString() ?: this.classifierOrFail.descriptor.name.toString()
    val collectionInterfaces = setOf(
        "kotlin.Array",
        "kotlin.collections.List",
        "kotlin.collections.MutableList",
        "kotlin.collections.Set",
        "kotlin.collections.MutableSet",
        "kotlin.collections.Map",
        "kotlin.collections.MutableMap"
    )

    return fqName in collectionInterfaces
}

fun IrType.determineCollectionEnum(): MMockCollectionEnum {
    return when (this.classOrNull?.descriptor?.fqNameSafe?.asString() ?: this.classifierOrFail.descriptor.name.toString()) {
        "kotlin.Array" -> MMockCollectionEnum.ARRAY
        "kotlin.collections.List" -> MMockCollectionEnum.LIST
        "kotlin.collections.MutableList" -> MMockCollectionEnum.MUTABLE_LIST
        "kotlin.collections.Set" -> MMockCollectionEnum.SET
        "kotlin.collections.MutableSet" -> MMockCollectionEnum.MUTABLE_SET
        "kotlin.collections.Map" -> MMockCollectionEnum.MAP
        "kotlin.collections.MutableMap" -> MMockCollectionEnum.MUTABLE_MAP
        else -> MMockCollectionEnum.NONE
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
