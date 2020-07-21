/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.compiler

import com.google.auto.service.AutoService
import com.intellij.mock.MockProject
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.compiler.plugin.ComponentRegistrar
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.extensions.DeclarationAttributeAltererExtension

lateinit var messageCollector: MessageCollector
lateinit var codegenDir: String

@AutoService(ComponentRegistrar::class)
class MMockComponentRegistrar : ComponentRegistrar {
    override fun registerProjectComponents(project: MockProject, configuration: CompilerConfiguration) {
        messageCollector = configuration.get(
            CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY,
            MessageCollector.NONE
        )
        codegenDir = configuration.get(
                MMockCommandLineProcessor.CODEGEN_DIR
        ) ?: throw IllegalStateException("No codegenDir configured")

        IrGenerationExtension.registerExtension(project, MMockIrGenerationExtension(codegenDir))
        DeclarationAttributeAltererExtension.registerExtension(project, MMockOpenExtension())
    }
}
