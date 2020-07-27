/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.compiler.codegen.ir

import com.pega.mmock.compiler.codegen.ConstructorCodeTemplate
import com.pega.mmock.compiler.codegen.ConstructorParameterCodeTemplate
import com.pega.mmock.compiler.codegen.utils.INVALID_CONSTRUCTOR_ARGS
import com.pega.mmock.compiler.messageCollector
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageLocation
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.ir.declarations.IrConstructor
import org.jetbrains.kotlin.ir.declarations.path
import org.jetbrains.kotlin.ir.util.file
import org.jetbrains.kotlin.ir.util.fileEntry
import org.jetbrains.kotlin.ir.util.hasDefaultValue

internal fun IrConstructor.toPrimaryCodeTemplate(): ConstructorCodeTemplate {
    return ConstructorCodeTemplate(
        this.valueParameters.map { ConstructorParameterCodeTemplate(it.name.toString(), it.type.generateCode(), it.hasDefaultValue()) }
    )
}

internal fun IrConstructor.checkConstraints() {
    val arguments = this.valueParameters

    if (arguments.isEmpty()) return

    arguments.forEach {
        if (!it.hasDefaultValue()) {
            if (!it.type.isMmockDefaultInstanceSupported())
                messageCollector.report(CompilerMessageSeverity.WARNING, INVALID_CONSTRUCTOR_ARGS, CompilerMessageLocation.Companion.create(
                    this.file.path,
                    this.fileEntry.getLineNumber(this.startOffset) + 1,
                    this.fileEntry.getColumnNumber(this.startOffset) + 1,
                    null)
                )
        }
    }
}
