package com.github.virelion.mmock.compiler.codegen.ir

import com.github.virelion.mmock.compiler.codegen.CodeTemplate
import com.github.virelion.mmock.compiler.codegen.MockClassCodeTemplate
import com.github.virelion.mmock.compiler.codegen.TypeParameterCodeTemplate
import java.lang.IllegalStateException
import org.jetbrains.kotlin.ir.declarations.IrClass
import org.jetbrains.kotlin.ir.declarations.IrProperty
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
import org.jetbrains.kotlin.ir.util.getPackageFragment

internal fun IrClass.toCodeTemplate(): CodeTemplate {
    return MockClassCodeTemplate(
            pkg = this.getPackageFragment()?.fqName?.asString() ?: throw IllegalStateException("No package"),
            originalName = this.name.asString(),
            imports = listOf(
                    "com.github.virelion.mmock.dsl.MMockContext",
                    "com.github.virelion.mmock.dsl.MockInitializer",
                    "com.github.virelion.mmock.backend.ObjectMock",
                    "com.github.virelion.mmock.backend.MockContainer"
            ),
            typeParameters = this.typeParameters.map {
                TypeParameterCodeTemplate(it.name.asString(), it.variance.label)
            },
            methods = this.declarations.filterIsInstance<IrSimpleFunction>().map { it.toCodeTemplate() },
            properties = this.declarations.filterIsInstance<IrProperty>().map { it.toCodeTemplate() }
    )
}
