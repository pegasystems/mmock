/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.dsl

typealias InvocationMatcher = (Int) -> Boolean

/**
 * Verify exact [amount] of invocations.
 */
@MMockDSL
fun VerificationContext.times(amount: Int): InvocationMatcher {
    return { it == amount }
}

/**
 * Verify that number of invocations is equal to 1.
 */
@MMockDSL
val VerificationContext.once: InvocationMatcher get() {
    return times(1)
}

/**
 * Verify that number of invocations is equal to 2.
 */
@MMockDSL
val VerificationContext.twice: InvocationMatcher get() {
    return times(2)
}

/**
 * Verify that statement hasn't been invoked.
 */
@MMockDSL
val VerificationContext.never: InvocationMatcher get() {
    return times(0)
}
