package com.github.virelion.mmock

sealed class MMockException(message: String?): RuntimeException(message)

class MMockVerificationException(message: String): MMockException(message)
class NoMethodStubException(message: String? = null): MMockException(message)