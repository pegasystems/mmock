package com.github.virelion.mmock.backend

sealed class MMockException(message: String): RuntimeException(message)

class MMockVerificationException(message: String): MMockException(message)