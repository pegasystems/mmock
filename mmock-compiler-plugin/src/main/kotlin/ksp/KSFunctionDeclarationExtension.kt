package ksp

import ksp.exception.InvalidConstructorException
import ksp.template.CodeTemplate
import ksp.template.ConstructorCodeTemplate
import ksp.template.ConstructorParameterCodeTemplate
import ksp.template.MethodCodeTemplate
import ksp.template.ParameterCodeTemplate
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageLocation
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.ir.declarations.path
import org.jetbrains.kotlin.ir.util.file
import org.jetbrains.kotlin.ir.util.fileEntry
import org.jetbrains.kotlin.ir.util.hasDefaultValue
import org.jetbrains.kotlin.ksp.symbol.KSFunctionDeclaration
import org.jetbrains.kotlin.ksp.symbol.Modifier

internal fun KSFunctionDeclaration.toCodeTemplate(): CodeTemplate {
    return MethodCodeTemplate(
            name = this.simpleName.getShortName(),
            returnType = this.returnType!!.resolve()!!.getQualifiedName(),
            typeParameters = this.typeParameters.map { it.name.getShortName() },
            parameters = this.parameters.map { ParameterCodeTemplate(it.name!!.getShortName(), it.type!!.resolve().toString()) },
            suspend = this.isSuspend()
    )
}

internal fun KSFunctionDeclaration.toConstructorCodeTemplate(): ConstructorCodeTemplate {
    return ConstructorCodeTemplate(
            this.parameters.map {
                ConstructorParameterCodeTemplate(
                        name = it.name!!.getShortName(),
                        type = it.type!!.resolve()!!.getQualifiedName(),
                        hasDefaultValue = it.hasDefault,
                        collectionEnum = it.type!!.determineCollectionEnum()
                )
            }
    )
}

fun KSFunctionDeclaration.isSuspend(): Boolean {
    modifiers.forEach {
        if (it == Modifier.SUSPEND)
            return true
    }

    return false
}

fun KSFunctionDeclaration.checkConstructorConstraints() {
    val arguments = this.parameters

    if (arguments.isEmpty()) return

    arguments.forEach {
        if (!it.hasDefault) {
            if (!it.type!!.isMmockDefaultInstanceSupported())
                throw InvalidConstructorException("${this.parentDeclaration!!.simpleName.getShortName()}: ${it.name!!.getShortName()}")
        }
    }
}
