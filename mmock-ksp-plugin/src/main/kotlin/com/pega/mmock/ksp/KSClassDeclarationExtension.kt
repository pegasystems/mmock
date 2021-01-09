package com.pega.mmock.ksp

import com.pega.mmock.ksp.template.CodeTemplate
import com.pega.mmock.ksp.template.MockClassCodeTemplate
import com.pega.mmock.ksp.template.TypeParameterCodeTemplate
import com.google.devtools.ksp.getDeclaredProperties
import com.google.devtools.ksp.isAbstract
import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSClassDeclaration

internal fun KSClassDeclaration.toCodeTemplate(): CodeTemplate {
    return MockClassCodeTemplate(
            pkg = this.packageName.asString(),
            imports = mutableListOf(
                    "com.pega.mmock.dsl.MMockContext",
                    "com.pega.mmock.dsl.MockInitializer",
                    "com.pega.mmock.backend.ObjectMock",
                    "com.pega.mmock.backend.MockContainer"
            ),
            typeParameters = this.typeParameters.map { TypeParameterCodeTemplate(it.name.getShortName(), it.variance) },
            originalName = this.simpleName.getShortName(),
            methods = this.getAllFunctions().map { it.toCodeTemplate() },
            properties = this.getDeclaredProperties().map { it.toCodeTemplate() },
            constructor = this.primaryConstructor?.toConstructorCodeTemplate()
    )
}

fun KSClassDeclaration.checkConstraints() {
    if (this.isAbstract() || this.classKind == ClassKind.INTERFACE) return

    if (this.primaryConstructor != null) {
        primaryConstructor!!.checkConstructorConstraints()
    }
}
