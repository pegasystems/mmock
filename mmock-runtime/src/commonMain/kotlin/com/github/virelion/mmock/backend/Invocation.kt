package com.github.virelion.mmock.backend

typealias Arguments = Array<Any?>

class Invocation<ReturnType>(
        val mock: MockObject,
        val name: String,
        val args: Arguments
) {


}