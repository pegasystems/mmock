package com.pega.mmock.compiler

import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor
import org.jetbrains.kotlin.descriptors.Modality
import org.jetbrains.kotlin.extensions.AnnotationBasedExtension
import org.jetbrains.kotlin.extensions.DeclarationAttributeAltererExtension
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.psi.KtModifierListOwner

class MMockOpenExtension : DeclarationAttributeAltererExtension {
    override fun refineDeclarationModality(
        modifierListOwner: KtModifierListOwner,
        declaration: DeclarationDescriptor?,
        containingDeclaration: DeclarationDescriptor?,
        currentModality: Modality,
        isImplicitModality: Boolean
    ): Modality? {
        val descriptor = declaration as? ClassDescriptor ?: containingDeclaration ?: return null
        if (currentModality != Modality.FINAL) return null

        if(descriptor.annotations.hasAnnotation(FqName("com.pega.mmock.GenerateMock"))) {
            return Modality.OPEN
        }

        return null
    }
}