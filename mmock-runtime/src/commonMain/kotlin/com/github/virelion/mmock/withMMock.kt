package com.github.virelion.mmock

import com.github.virelion.mmock.dsl.MMockContext

expect fun withMMock(block: suspend MMockContext.() -> Unit)
