package com.github.virelion.mmock.dsl

/**
 * Common: eq(smt), any(), instanceOf()
 * Verification: verify { recording() called once/twice/never/times(1) }
 *      recording : Verification(recording(n), verificationRule)
 * Stubbing: every { recording() } returns smt
 *
 * recording(): List<Invocation> ->
 *
 */

@DslMarker
annotation class MMockDSL

