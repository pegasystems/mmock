package com.github.virelion.mmock.compiler.codegen.jvm

import com.github.virelion.mmock.compiler.codegen.MockClassCodeTemplate
import org.jetbrains.kotlin.backend.common.serialization.findPackage
import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.descriptors.PropertyDescriptor
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor

internal fun ClassDescriptor.toCodeTemplate(): MockClassCodeTemplate {
    return MockClassCodeTemplate(
            pkg = findPackage().fqName.asString(),
            originalName = name.asString(),
            typeParameters = this.declaredTypeParameters.map { it.toCodeString() },
            imports = listOf(
                    "com.github.virelion.mmock.dsl.MMockContext",
                    "com.github.virelion.mmock.dsl.MockInitializer",
                    "com.github.virelion.mmock.backend.ObjectMock",
                    "com.github.virelion.mmock.backend.MockContainer"
            ),
            methods = unsubstitutedMemberScope
                    .getContributedDescriptors()
                    .filterIsInstance<SimpleFunctionDescriptor>()
                    .map { it.toCodeTemplate() },
            properties = unsubstitutedMemberScope
                    .getContributedDescriptors()
                    .filterIsInstance<PropertyDescriptor>()
                    .map { it.toCodeTemplate() }
    )
}
