package ksp

import ksp.exception.InvalidConstructorException
import ksp.template.CodeTemplate
import ksp.template.MockClassCodeTemplate
import ksp.template.TypeParameterCodeTemplate
import org.jetbrains.kotlin.ksp.getDeclaredProperties
import org.jetbrains.kotlin.ksp.isAbstract
import org.jetbrains.kotlin.ksp.symbol.ClassKind
import org.jetbrains.kotlin.ksp.symbol.KSClassDeclaration

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

fun KSClassDeclaration.hasGenerateMockAnnotation(): Boolean {
    annotations.forEach {
        if (it.shortName.asString() == "GenerateMock" && it.annotationType.resolve()?.declaration?.qualifiedName?.asString() == "com.pega.mmock.GenerateMock")
            return true
    }

    return false
}

fun KSClassDeclaration.checkConstraints() {
    if (this.isAbstract() || this.classKind == ClassKind.INTERFACE) return

    if(this.primaryConstructor != null){
        primaryConstructor!!.checkConstructorConstraints()
    }
}
