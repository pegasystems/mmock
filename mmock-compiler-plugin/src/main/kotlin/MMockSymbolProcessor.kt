import com.google.auto.service.AutoService
import ksp.checkConstraints
import ksp.exception.InvalidConstructorException
import ksp.toCodeTemplate
import ksp.utils.CodeBuilder
import ksp.utils.streamAndClose
import org.jetbrains.kotlin.ksp.processing.CodeGenerator
import org.jetbrains.kotlin.ksp.processing.KSPLogger
import org.jetbrains.kotlin.ksp.processing.Resolver
import org.jetbrains.kotlin.ksp.processing.SymbolProcessor
import org.jetbrains.kotlin.ksp.symbol.KSClassDeclaration
import kotlin.concurrent.thread

@AutoService(SymbolProcessor::class)
class MMockSymbolProcessor : SymbolProcessor {

    lateinit var codeGenerator: CodeGenerator
    lateinit var logger: KSPLogger

    override fun finish() {

    }

    override fun init(options: Map<String, String>, kotlinVersion: KotlinVersion, codeGenerator: CodeGenerator, logger: KSPLogger) {
        this.codeGenerator = codeGenerator
        this.logger = logger
    }

    override fun process(resolver: Resolver) {
        val files = resolver.getSymbolsWithAnnotation("com.pega.mmock.GenerateMock")
                .filterIsInstance<KSClassDeclaration>()
                .forEach {
                    it.checkConstraints()
                    val classFile = codeGenerator.createNewFile(
                        it.packageName.asString(),
                        "${it.simpleName.getShortName()}_Mock")

                    classFile.streamAndClose(it.toCodeTemplate().generate(CodeBuilder()))
                }
    }
}
