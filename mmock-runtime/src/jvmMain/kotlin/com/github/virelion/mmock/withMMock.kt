package com.github.virelion.mmock

import com.github.virelion.mmock.backend.runSuspend
import com.github.virelion.mmock.dsl.MMockContext

actual fun withMMock(block: suspend MMockContext.() -> Unit) = runSuspend {
    MMockContext().apply { block() }
}
