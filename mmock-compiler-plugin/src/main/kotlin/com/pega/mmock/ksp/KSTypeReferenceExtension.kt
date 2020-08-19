package com.pega.mmock.ksp

import com.pega.mmock.ksp.utils.MMockCollectionEnum
import org.jetbrains.kotlin.ksp.symbol.KSTypeReference

internal fun KSTypeReference.getReferenceName(): String {
    return "${this.resolve()!!.declaration.qualifiedName!!.getQualifier()}.${this.element}"
}

internal fun KSTypeReference.isCollectionInterface(): Boolean {
    val name = this.getReferenceName()
    val collectionInterfaces = setOf(
            "kotlin.Array",
            "kotlin.collections.List",
            "kotlin.collections.MutableList",
            "kotlin.collections.Set",
            "kotlin.collections.MutableSet",
            "kotlin.collections.Map",
            "kotlin.collections.MutableMap"
    )

    return name in collectionInterfaces
}

internal fun KSTypeReference.isPrimitiveArray(): Boolean {
    val name = this.getReferenceName()
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

    return name in primitiveArrays
}

internal fun KSTypeReference.isPrimitive(): Boolean {
    val name = this.getReferenceName()
    val primitiveArrays = setOf(
        "kotlin.Int",
        "kotlin.Short",
        "kotlin.Byte",
        "kotlin.Long",
        "kotlin.Boolean",
        "kotlin.UInt",
        "kotlin.UShort",
        "kotlin.UByte",
        "kotlin.ULong",
        "kotlin.Double",
        "kotlin.Float",
        "kotlin.Char"
    )

    return name in primitiveArrays
}

internal fun KSTypeReference.determineCollectionEnum(): MMockCollectionEnum {
    return when (this.getReferenceName()) {
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

internal fun KSTypeReference.isMmockDefaultInstanceSupported(): Boolean {
    return this.isPrimitiveArray() || this.isCollectionInterface() || this.isPrimitive()
}
