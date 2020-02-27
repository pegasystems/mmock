package com.github.virelion.mmock.backend

typealias ArgumentsVerificationFunction = (Any?) -> Boolean

fun Arguments.verify(constraint: Array<ArgumentsVerificationFunction>): Boolean {
    var result = true
    for(i in this.indices) {
        result = result && constraint[i].invoke(this[i])
    }
    return result
}