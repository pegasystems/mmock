/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.backend

import com.pega.mmock.MMockRecordingException

typealias ArgumentsVerificationFunction = (Any?) -> Boolean
typealias ArgumentsConstraints = MutableList<ArgumentsVerificationFunction>

fun ArgumentsConstraints.verify(constraint: Array<out Any?>): Boolean {
    if (this.size != constraint.size) throw MMockRecordingException("Matcher and argument counts are different. Please use all matcher or all argument notation.")
    var result = true
    for (i in this.indices) {
        result = result && this[i].invoke(constraint[i])
    }
    return result
}
