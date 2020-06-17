/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.scenarios

import com.pega.mmock.dsl.any
import com.pega.mmock.samples.ExampleInterface
import com.pega.mmock.withMMock
import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertFailsWith

class SuspendFunctionScenario {
    @Test
    @JsName("Mock_can_throw_exception")
    fun `Mock can throw exception`() = withMMock {
        val myMock = mock.ExampleInterface()
        every { myMock.suspendFunction(any()) } throws IllegalStateException()

        assertFailsWith<IllegalStateException> {
            myMock.suspendFunction(1)
        }
    }
}
