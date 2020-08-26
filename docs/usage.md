# Guide
To create mock annotate interface with `GenerateMock` annotation.

```kotlin
package com.example

import com.pega.mmock.GenerateMock

@GenerateMock
interface MyInterface
```

This will cause extension function for creating mocks.
You can use it in test

```kotlin
import com.example.MyInterface
import com.pega.mmock.withMMock

class ExampleTest {
    @Test
    fun example() = withMMock {
        val myInterface = mock.MyInterface()
    }   
}
```

## Regular function
To define behaviour of mock function use `every` block

```kotlin
@GenerateMock
interface MyInterface {
    fun exampleFunction(arg: Int): Int
}
```

```kotlin
import com.example.MyInterface
import com.pega.mmock.withMMock

class ExampleTest {
    @Test
    fun example() = withMMock {
        val myInterface = mock.MyInterface()
        
        // You can use multiple bindings with matchers
        every { myInterface.exampleFunction(1) } returns 1
        every { myInterface.exampleFunction(eq(2)) } returns 2
        every { myInterface.exampleFunction(any()) } returns 3

        assertEquals(1, myInterface.exampleFunction(1))
        assertEquals(2, myInterface.exampleFunction(2))
        assertEquals(3, myInterface.exampleFunction(3))
        assertEquals(3, myInterface.exampleFunction(4))
    }   
}
```

## Suspend functions
Suspend functions are supported. 
You can mock them the same way as regular functions since `withMMock`
is already launched in coroutine context.

```kotlin
@GenerateMock
interface MyInterface {
    suspend fun exampleSuspendFunction(arg: Int): Int
}
```

```kotlin
import com.example.MyInterface
import com.pega.mmock.withMMock

class ExampleTest {
    @Test
    fun example() = withMMock {
        val myInterface = mock.MyInterface()
        
        // You can use multiple bindings with matchers
        every { myInterface.exampleSuspendFunction(any()) } returns 1

        assertEquals(1, myInterface.exampleSuspendFunction(1))
    }   
}
```

## Throwing exceptions
Use `throws` DSL to define exception thrown out of mock.

```kotlin
import com.example.MyInterface
import com.pega.mmock.withMMock

class ExampleTest {
    @Test
    fun example() = withMMock {
        val myInterface = mock.MyInterface()
        
        every { myInterface.exampleSuspendFunction(any()) } throws IllegalStateException()
    }   
}
```

## Matchers
To define equality matchers use `eq(<value>)` matcher. 
It can also be accomplished by using value directly.

To define any argument matcher use `any()` matcher.

To define any collection argument matcher use one that is applicable from:
- `anyArray<>()`, `anyList<>()`, `anyMap<>()`, `anySet<>()`

```kotlin
@GenerateMock
interface MyInterface {
    fun exampleFunction(arg: List<Int>): Int
}
```

```kotlin
import com.example.MyInterface
import com.pega.mmock.withMMock

class ExampleTest {

    class DelegatedList<T>(private val delegate: List<T> = listOf()) : List<T> by delegate
    class DelegatedMutableList<T>(private val delegate: MutableList<T> = mutableListOf()) : MutableList<T> by delegate

    @Test
    fun example() = withMMock {
        val myInterface = mock.MyInterface()
        
        every { myInterface.exampleFunction(anyList()) } returns 2
        every { myInterface.exampleFunction(anyList<MutableList<Int>, Int>()) } returns 3

        assertEquals(2, myInterface.exampleFunction(DelegatedList(listOf(1, 2, 3))))
        assertEquals(3, myInterface.exampleFunction(DelegatedMutableList(mutableListOf(1, 2, 3))))
    }   
}
```

To define custom matcher use `on { <custom boolean expression> }`

```kotlin
@GenerateMock
interface MyInterface {
    fun exampleFunction(arg: Int): Int
}
```

```kotlin
import com.example.MyInterface
import com.pega.mmock.withMMock

class ExampleTest {
    @Test
    fun example() = withMMock {
        val myInterface = mock.MyInterface()
        
        every { myInterface.exampleFunction(on { it > 4 }) } returns 2
        every { myInterface.exampleFunction(on { it > 1 }) } returns 1
        every { myInterface.exampleFunction(any()) } returns 0
    }   
}
```

You can use ```onArray<> {}```, ```onList<> {}```, ```onSet<> {}``` and ```onMap<> {}``` to define custom matcher for collections.

Mixing direct value notation and matcher notation results in 
`MMockRecordingException`. To fix that replace all direct value matchers with `eq(<value>)`

## Verification
Library allows for invocation count and order/sequence verification.
Use `verify {}` to accomplish that.

```kotlin
@GenerateMock
interface MyInterface {
    fun exampleFunction(arg: Int): Int
}
```

```kotlin
class ExampleTest {
    @Test
    fun example() = withMMock {
        val myInterface = mock.MyInterface()
        
        every { myInterface.exampleFunction(any()) } returns 1

        myInterface.exampleFunction(1)
        myInterface.exampleFunction(1)
        myInterface.exampleFunction(2)
        myInterface.exampleFunction(3)
        myInterface.exampleFunction(4)

        verify {
            invocation { myInterface.exampleFunction(1) } called twice
            invocation { myInterface.exampleFunction(2) } called once

            //if not specified `called once` is put as default
            invocation { myInterface.exampleFunction(3) }
            invocation { myInterface.exampleFunction(4) }

            invocation { myInterface.exampleFunction(any()) } called times(5)

            //you can use comparisons as well
            invocation { myInterface.exampleFunction(any()) } called { it > 3 }

            order {
                invocation { myInterface.exampleFunction(1) }
                invocation { myInterface.exampleFunction(4) }
            }

            sequence {
                invocation { myInterface.exampleFunction(2) }
                invocation { myInterface.exampleFunction(3) }
                invocation { myInterface.exampleFunction(4) }
            }
        }
    }   
}
```