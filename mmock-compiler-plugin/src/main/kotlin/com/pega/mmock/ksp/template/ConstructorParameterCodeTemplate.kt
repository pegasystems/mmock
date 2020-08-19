package com.pega.mmock.ksp.template

import com.pega.mmock.ksp.utils.MMockCollectionEnum

class ConstructorParameterCodeTemplate(
    val name: String,
    val type: String,
    val hasDefaultValue: Boolean,
    val collectionEnum: MMockCollectionEnum
) {
    override fun toString(): String = "override val $name: $type"

    fun getDefaultInstanceFunction(): String =
            "$name = ${getDefaultInstanceString()}()"

    fun getDefaultInstanceImport(): String =
            "com.pega.mmock.backend.unsafe." + getDefaultInstanceString()

    private fun getDefaultInstanceString(): String {
        return when (collectionEnum) {
            MMockCollectionEnum.ARRAY -> "defaultArrayInstance"
            MMockCollectionEnum.LIST -> "defaultListInstance"
            MMockCollectionEnum.MUTABLE_LIST -> "defaultMutableListInstance"
            MMockCollectionEnum.MAP -> "defaultMapInstance"
            MMockCollectionEnum.MUTABLE_MAP -> "defaultMutableMapInstance"
            MMockCollectionEnum.SET -> "defaultSetInstance"
            MMockCollectionEnum.MUTABLE_SET -> "defaultMutableSetInstance"
            MMockCollectionEnum.NONE -> "defaultInstance"
        }
    }
}
