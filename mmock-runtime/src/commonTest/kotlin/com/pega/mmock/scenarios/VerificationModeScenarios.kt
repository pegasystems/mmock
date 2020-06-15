/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.scenarios

import com.pega.mmock.MMockVerificationException
import com.pega.mmock.dsl.any
import com.pega.mmock.dsl.eq
import com.pega.mmock.dsl.once
import com.pega.mmock.dsl.twice
import com.pega.mmock.samples.ExampleInterface
import com.pega.mmock.withMMock
import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class VerificationModeScenarios {
    @Test
    @JsName("Verify_order")
    fun `Verify order`() = withMMock {
        val myMock = mock.ExampleInterface()
        every { myMock.function(any()) } returns 1
        every { myMock.suspendFunction(any()) } returns 1
        every { myMock.noArgsFunction() } returns 1

        assertEquals(1, myMock.function(1))
        assertEquals(1, myMock.function(1))
        assertEquals(1, myMock.suspendFunction(1))
        assertEquals(1, myMock.noArgsFunction())

        verify {
            order {
                invocation { myMock.function(any()) }
                invocation { myMock.noArgsFunction() }
            }
        }

        verify {
            order {
                invocation { myMock.function(any()) }
                invocation { myMock.suspendFunction(any()) }
            }
        }

        verify {
            order {
                invocation { myMock.suspendFunction(any()) }
                invocation { myMock.noArgsFunction() }
            }
        }

        verify {
            order {
                invocation { myMock.suspendFunction(eq(1)) }
                invocation { myMock.noArgsFunction() }
            }
        }

        assertFailsWith<MMockVerificationException> {
            verify {
                order {
                    invocation { myMock.function(eq(2)) }
                    invocation { myMock.noArgsFunction() }
                }
            }
        }

        assertFailsWith<MMockVerificationException> {
            verify {
                order {
                    invocation { myMock.function(eq(1)) }
                    invocation { myMock.suspendFunction(eq(2)) }
                }
            }
        }

        assertFailsWith<MMockVerificationException> {
            verify {
                order {
                    invocation { myMock.suspendFunction(eq(2)) }
                    invocation { myMock.noArgsFunction() }
                }
            }
        }

        assertFailsWith<MMockVerificationException> {
            verify {
                order {
                    invocation { myMock.noArgsFunction() }
                    invocation { myMock.function(any()) }
                }
            }
        }

        assertFailsWith<MMockVerificationException> {
            verify {
                order {
                    invocation { myMock.suspendFunction(any()) }
                    invocation { myMock.function(any()) }
                }
            }
        }

        assertFailsWith<MMockVerificationException> {
            verify {
                order {
                    invocation { myMock.noArgsFunction() }
                    invocation { myMock.suspendFunction(any()) }
                }
            }
        }
    }

    @Test
    @JsName("Verify_sequence")
    fun `Verify sequence`() = withMMock {
        val myMock = mock.ExampleInterface()

        every { myMock.function(any()) } returns 1
        every { myMock.suspendFunction(any()) } returns 1
        every { myMock.noArgsFunction() } returns 1

        assertEquals(1, myMock.function(1))
        assertEquals(1, myMock.function(2))
        assertEquals(1, myMock.suspendFunction(1))
        assertEquals(1, myMock.noArgsFunction())

        verify {
            sequence {
                invocation { myMock.function(any()) }
                invocation { myMock.function(eq(2)) }
            }
        }

        verify {
            sequence {
                invocation { myMock.function(any()) }
                invocation { myMock.function(any()) }
                invocation { myMock.suspendFunction(any()) }
                invocation { myMock.noArgsFunction() }
            }
        }

        verify {
            sequence {
                invocation { myMock.function(any()) }
                invocation { myMock.suspendFunction(any()) }
                invocation { myMock.noArgsFunction() }
            }
        }

        assertFailsWith<MMockVerificationException> {
            verify {
                sequence {
                    invocation { myMock.function(eq(1)) }
                    invocation { myMock.suspendFunction(any()) }
                }
            }
        }

        assertFailsWith<MMockVerificationException> {
            verify {
                sequence {
                    invocation { myMock.noArgsFunction() }
                    invocation { myMock.suspendFunction(any()) }
                }
            }
        }

        assertFailsWith<MMockVerificationException> {
            verify {
                sequence {
                    invocation { myMock.function(eq(2)) }
                    invocation { myMock.noArgsFunction() }
                }
            }
        }
    }

    @Test
    @JsName("Combined_scenario")
    fun `Combined scenario`() = withMMock {
        val myMock = mock.ExampleInterface()

        every { myMock.suspendFunction(any()) } returns 1
        every { myMock.function(any()) } returns 1

        myMock.suspendFunction(1)
        myMock.function(1)
        myMock.function(2)
        myMock.suspendFunction(2)

        verify {
            invocation { myMock.suspendFunction(1) } called once
            invocation { myMock.function(1) } called once
            invocation { myMock.function(2) } called once
            invocation { myMock.suspendFunction(2) } called once
            invocation { myMock.suspendFunction(any()) } called twice
            invocation { myMock.function(any()) } called twice

            sequence {
                invocation { myMock.suspendFunction(1) }
                invocation { myMock.function(1) }
                invocation { myMock.function(2) }
                invocation { myMock.suspendFunction(2) }
            }

            order {
                invocation { myMock.suspendFunction(1) }
                invocation { myMock.function(1) }
                invocation { myMock.function(2) }
                invocation { myMock.suspendFunction(2) }
            }
        }
    }
}
