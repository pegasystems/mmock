package com.pega.mmock.ksp

import com.pega.mmock.ksp.template.PropertyCodeTemplate
import com.google.devtools.ksp.symbol.KSPropertyDeclaration

internal fun KSPropertyDeclaration.toCodeTemplate(): PropertyCodeTemplate {
    return PropertyCodeTemplate(
            name = this.simpleName.getShortName(),
            type = this.type!!.resolve()!!.getQualifiedName(),
            mutable = this.isMutable
    )
}
