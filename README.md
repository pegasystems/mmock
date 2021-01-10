# MMock - mocking library for Kotlin Multiplatform

[![Build Status](https://travis-ci.org/pegasystems/mmock.svg?branch=master)](https://travis-ci.org/pegasystems/mmock)
![badge](https://img.shields.io/badge/platform-JVM-orange)
![badge](https://img.shields.io/badge/platform-JS-red)
![badge](https://img.shields.io/badge/platform-iOS--64-yellow)
![badge](https://img.shields.io/badge/platform-Android-brightgreen)
![badge](https://img.shields.io/badge/platform-Linux--64-blue)
![badge](https://img.shields.io/badge/platform-MinGW--64-blueviolet)

Multiplatform Kotlin library for mocking. 

## How to use
1\. Add plugin to gradle:

```build.gradle.kts
plugins {
    // ...
    id("com.pega.mmock") version <plugin-version>
}


// ...

kotlin {
    // ...
    sourceSets {
        val commonMain by gettting {
            dependencies {
                // ...
                implementation("com.pega.mmock:mmock-runtime:$mmockVersion")
            }
        }
    }
}
```

2\. Annotate interface
```kotlin
package com.example

import com.pega.mmock.GenerateMock

@GenerateMock
interface MyInteface {
    fun myFunction(arg1: Int): Int
}
```

3\. Use mocks in tests

```kotlin
package com.example

import com.pega.mmock.dsl.any
import com.pega.mmock.dsl.once
import com.pega.mmock.withMMock

class TestClass {
    @Test
    fun exampleTest() = withMMock {
        val myInterface = mock.MyInterface()
        every { myInterface.myFunction(any()) } returns 2

        assertEquals(2, myInterface.myFunction(1))
        
        verify {
            invocation { myInterface.myFunction(any()) } called once
        }   
    }   
}
```

For more detailed user guide go to [usage.md](docs/usage.md)