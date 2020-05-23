package com.github.virelion.mmock.compiler

import com.google.auto.service.AutoService
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.codegen.extensions.ExpressionCodegenExtension
import org.jetbrains.kotlin.com.intellij.mock.MockProject
import org.jetbrains.kotlin.compiler.plugin.ComponentRegistrar
import org.jetbrains.kotlin.config.CompilerConfiguration

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

        ExpressionCodegenExtension.registerExtension(
                project,
                MMockExpressionCodegenExtension(codegenDir)
        )
    }
}
