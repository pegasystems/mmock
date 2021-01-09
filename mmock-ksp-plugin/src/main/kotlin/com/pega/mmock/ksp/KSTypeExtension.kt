package com.pega.mmock.ksp

import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.symbol.KSTypeParameter

internal fun KSType.getQualifiedName(): String {
    return if (this.isGeneric())
        this.toString()
    else
        "${this.declaration.qualifiedName!!.getQualifier()}.$this"
}

internal fun KSType.isGeneric(): Boolean {
    return this.declaration is KSTypeParameter
}
