package com.github.virelion.mmock

import com.github.virelion.mmock.backend.MMockVerificationException
import com.github.virelion.mmock.dsl.MMockContext
import com.github.virelion.mmock.dsl.VerificationContext
import kotlin.test.assertFailsWith

fun MMockContext.verifyFailed(block: VerificationContext.() -> Unit) {
    assertFailsWith<MMockVerificationException> {
        verify(block)
    }
}