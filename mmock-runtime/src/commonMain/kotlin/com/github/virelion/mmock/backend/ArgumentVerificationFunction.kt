package com.github.virelion.mmock.backend

import com.github.virelion.mmock.MMockRecordingException

typealias ArgumentsVerificationFunction = (Any?) -> Boolean
typealias ArgumentsConstraints = MutableList<ArgumentsVerificationFunction>

fun ArgumentsConstraints.verify(constraint: Array<out Any?>): Boolean {
    if(this.size != constraint.size) throw MMockRecordingException("Matcher and argument counts are different. Please use all matcher or all argument notation.")
    var result = true
    for(i in this.indices) {
        result = result && this[i].invoke(constraint[i])
    }
    return result
}