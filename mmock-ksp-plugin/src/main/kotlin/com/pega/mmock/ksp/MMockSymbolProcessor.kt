package com.pega.mmock.ksp

import com.google.auto.service.AutoService
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.pega.mmock.ksp.template.MockClassCodeTemplate

@AutoService(SymbolProcessor::class)
class MMockSymbolProcessor : SymbolProcessor {
    lateinit var logger: KSPLogger
    lateinit var mmockCodegenDir: String

    override fun finish() {
        logger.info("MMockSymbolProcessor codegeneration finished")
    }

    override fun init(options: Map<String, String>, kotlinVersion: KotlinVersion, codeGenerator: CodeGenerator, logger: KSPLogger) {
        this.mmockCodegenDir = requireNotNull(options["mmockCodegenDir"]) {
            "mmockCodegenDir ksp option should have correct path".apply { logger.error(this) }
        }
        this.logger = logger
        logger.info("MMockSymbolProcessor initiated")
    }

    override fun process(resolver: Resolver) {
        logger.info("MMockSymbolProcessor processing started")
        val mockClasses = resolver.getSymbolsWithAnnotation("com.pega.mmock.GenerateMock")
                .asSequence()
                .filterIsInstance<KSClassDeclaration>()
                .map {
                    it.checkConstraints()
                    it.toCodeTemplate()
                }
                .filterIsInstance<MockClassCodeTemplate>()

        PackageStreamer(mmockCodegenDir).stream(mockClasses)
    }
}
