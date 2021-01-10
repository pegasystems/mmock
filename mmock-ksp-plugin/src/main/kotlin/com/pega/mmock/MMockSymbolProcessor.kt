package com.pega.mmock

import com.google.auto.service.AutoService
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.pega.mmock.ksp.checkConstraints
import com.pega.mmock.ksp.toCodeTemplate
import com.pega.mmock.ksp.utils.CodeBuilder
import com.pega.mmock.ksp.utils.streamAndClose

@AutoService(SymbolProcessor::class)
class MMockSymbolProcessor : SymbolProcessor {

    lateinit var codeGenerator: CodeGenerator
    lateinit var logger: KSPLogger

    override fun finish() {
        logger.info("MMockSymbolProcessor codegeneration finished")
    }

    override fun init(options: Map<String, String>, kotlinVersion: KotlinVersion, codeGenerator: CodeGenerator, logger: KSPLogger) {
        this.codeGenerator = codeGenerator
        this.logger = logger
        logger.info("MMockSymbolProcessor initiated")
    }

    override fun process(resolver: Resolver) {
        logger.info("MMockSymbolProcessor processing started")
        val files = resolver.getSymbolsWithAnnotation("com.pega.mmock.GenerateMock")
                .filterIsInstance<KSClassDeclaration>()
                .forEach {
                    it.checkConstraints()
                    val classFile = codeGenerator.createNewFile(
                        dependencies = Dependencies.ALL_FILES,
                        packageName = it.packageName.asString(),
                        fileName = "${it.simpleName.getShortName()}_Mock",
                        extensionName = "kt"
                    )

                    classFile.streamAndClose(it.toCodeTemplate().generate(CodeBuilder()))
                }
    }
}
