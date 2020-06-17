/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.compiler

import com.google.auto.service.AutoService
import org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
import org.jetbrains.kotlin.compiler.plugin.CliOption
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.CompilerConfigurationKey

@AutoService(CommandLineProcessor::class)
class MMockCommandLineProcessor : CommandLineProcessor {
    companion object {
        val CODEGEN_DIR: CompilerConfigurationKey<String> = CompilerConfigurationKey.create("codegenDir")
    }

    override val pluginId: String = "mmock-codegen"
    override val pluginOptions: Collection<CliOption> = listOf(
            CliOption("codegenDir", "<String>", "output directory"))

    override fun processOption(
        option: AbstractCliOption,
        value: String,
        configuration: CompilerConfiguration
    ) = when (option.optionName) {
        "codegenDir" -> configuration.put(CODEGEN_DIR, value)
        else -> error("Unexpected config option ${option.optionName}")
    }
}
