/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.demo

import com.pega.mmock.dsl.any
import com.pega.mmock.dsl.once
import com.pega.mmock.dsl.twice
import com.pega.mmock.withMMock
import kotlin.test.Test
import kotlin.test.assertEquals

class InterfaceWithGenericsTest {
    @Test
    fun function() = withMMock {
        val mock = mock.InterfaceWithGenerics<Int, Int, Int>()
        every { mock.function(1) } returns 1
        every { mock.function(2) } returns 2

        assertEquals(1, mock.function(1))
        assertEquals(2, mock.function(2))

        verify {
            invocation { mock.function(1) } called once
            invocation { mock.function(2) } called once
            invocation { mock.function(any()) } called twice
        }
    }
}
