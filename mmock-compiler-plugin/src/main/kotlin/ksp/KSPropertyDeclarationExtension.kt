package ksp

import ksp.template.PropertyCodeTemplate
import org.jetbrains.kotlin.ksp.symbol.KSPropertyDeclaration

internal fun KSPropertyDeclaration.toCodeTemplate(): PropertyCodeTemplate {
    return PropertyCodeTemplate(
            name = this.simpleName.getShortName(),
            type = this.type!!.resolve()!!.getQualifiedName(),
            mutable = this.getter != null
    )
}
