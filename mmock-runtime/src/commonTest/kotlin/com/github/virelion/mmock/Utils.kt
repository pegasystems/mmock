package com.github.virelion.mmock

import com.github.virelion.mmock.dsl.MMockContext
import com.github.virelion.mmock.dsl.VerificationContext
import kotlin.test.assertFailsWith

suspend fun MMockContext.verifyFailed(block: suspend VerificationContext.() -> Unit) {
    assertFailsWith<MMockVerificationException> {
        verify(block)
    }
}
