# MMock design

MMock leverages multiplatform architecture to allow all platforms work the same way.

## Code generation
Unlike other mocking libraries for Java and Kotlin, MMock cannot use reflection due to Kotlin Native limited support.

Because of that Mock classes are generated via compiler-plugin for Kotlin.

Code generation is made based on `IrGenerationExtension`, but instead of generating LLVM IR, 
plugin is used to analyze interface structures and generate commonTest code.

Code generation product is located in `build/generated/mmock/commonTest`.

## Runtime
Runtime library is made using Kotlin Multiplatform to support usage of generated code.

### Unsafe instance
There is a way to created unsafe instance of objects to trick Kotlin compiler into thinking something is returned out of
argument matcher functions (`any`, `eq`, `instanceOf`).

This is accomplished differently for every platform:
- JVM - Usage of `sun.misc.Unsafe` for classes and `java.lang.reflect.Proxy` for interfaces
- JS - Force casted `{}` object
- Native - Refernce to null created by `kotlin.native.internal.Ref`

### Mock classes structure
#### Interface mocking 
Because interfaces in Kotlin are always open and does not have constructors.
Generating code from function definitions into generic mock objects is safe

#### Class mocking
Classes can be `final` and can have constructors, so there is a limit for what can be safely done.
More about the topic defined in [class-mock-design.md]("class-mocks-design.md")