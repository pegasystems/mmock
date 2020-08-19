package ksp

import org.jetbrains.kotlin.ksp.symbol.KSType
import org.jetbrains.kotlin.ksp.symbol.KSTypeParameter

internal fun KSType.getQualifiedName(): String {
    return if (this.isGeneric())
        this.toString()
    else
        "${this.declaration.qualifiedName!!.getQualifier()}.$this"
}

internal fun KSType.isGeneric(): Boolean {
    return this.declaration is KSTypeParameter
}
