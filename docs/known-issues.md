# Known issues
## Code generation is impossible on non-native targets
IR compiler plugin is running only on native targets, gradle plugin makes sure that it will always run first.
This means that if you want to run unit tests with mocks, you need to have native target setup.

This makes it especially annoying if you have standard mobile project with android and ios target, 
because you require macOS and Xcode even for Android developers, just to run codegen.

This issue can be worked around by extracting generated code into test code files and committing it into repository.
Then `@GenerateMock` annotation can be removed and no codegen is required to run before unit tests.

This issue will be addressed with migration to Kotlin Symbol Processing library instead of IR compiler plugin.

## JVM/Android target
- Sealed class has to have at least one sub-class in order to be used with ```any()``` matcher.

