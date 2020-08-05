/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.samples

import com.pega.mmock.dsl.any
import com.pega.mmock.dsl.eq
import com.pega.mmock.withMMock

class DSLSample {
    fun s1() = withMMock {
        val exampleInterface = mock.ExampleInterface()

        every { exampleInterface.noArgsFunction() } returns 1
        every { exampleInterface.function(any()) } returns 1
        every { exampleInterface.function(any<Int>()) } returns 1
        every { exampleInterface.function(eq(1)) } returns 1
    }
}
