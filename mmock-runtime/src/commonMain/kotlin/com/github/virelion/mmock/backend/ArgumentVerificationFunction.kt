package com.github.virelion.mmock.backend

typealias ArgumentsVerificationFunction = (Any?) -> Boolean
typealias ArgumentsConstraints = MutableList<ArgumentsVerificationFunction>

fun ArgumentsConstraints.verify(constraint: Array<out Any?>): Boolean {
    var result = true
    for(i in this.indices) {
        result = result && this[i].invoke(constraint[i])
    }
    return result
}