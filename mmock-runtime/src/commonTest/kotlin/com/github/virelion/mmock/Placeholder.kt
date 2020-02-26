package com.github.virelion.mmock

import kotlin.test.Test
import kotlin.test.assertEquals

class PlaceholderTest {

    @Test
    fun placeholderTest() {
        assertEquals("Placeholder", Placeholder().content)
    }
}