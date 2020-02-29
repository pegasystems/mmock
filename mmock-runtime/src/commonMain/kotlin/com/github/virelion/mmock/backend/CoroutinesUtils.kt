package com.github.virelion.mmock.backend

expect fun <T> runSuspend(block: suspend () -> T): T