# MMock design

MMock leverages multiplatform architecture to allow all platforms work the same way.

# Code generation
Unlike other mocking libraries for Java and Kotlin, MMock cannot use reflection due to Kotlin Native limited support.

Because of that Mock classes are generated via compiler-plugin for Kotlin.

Code generation is made based on `IrGenerationExtension`, but instead of generating LLVM IR, 
plugin is used to analyze interface structures and generate commonTest code.

Code generation product is located in `build/generated/mmock/commonTest`.

# Runtime
Runtime library is made using Kotlin Multiplatform to support usage of generated code.

## Unsafe instance
There is a way to created unsafe instance of objects to trick Kotlin compiler into thinking something is returned out of
argument matcher functions (`any`, `eq`, `instanceOf`).

This is accomplished differently for every platform:
- JVM - Usage of `sun.misc.Unsafe` for classes and `java.lang.reflect.Proxy` for interfaces
- JS - Force casted `{}` object
- Native - Refernce to null created by `kotlin.native.internal.Ref`