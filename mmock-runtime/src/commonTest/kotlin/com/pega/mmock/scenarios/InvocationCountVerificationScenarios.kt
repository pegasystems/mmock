/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.scenarios

import com.pega.mmock.dsl.twice
import com.pega.mmock.samples.ExampleInterface
import com.pega.mmock.verifyFailed
import com.pega.mmock.withMMock
import kotlin.js.JsName
import kotlin.test.Test

class InvocationCountVerificationScenarios {
    @Test
    @JsName("Assumed_called_once_when_not_specified")
    fun `Assumed called once when not specified`() = withMMock {
        val mock = mock.ExampleInterface()
        every { mock.function(1) } returns 1

        mock.function(1)

        verify {
            invocation { mock.function(1) }
        }

        verifyFailed { invocation { mock.function(2) } }
        verifyFailed { invocation { mock.function(2) } called twice }
    }
}
