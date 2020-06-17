/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.backend.stack

import com.pega.mmock.backend.ArgumentsVerificationFunction
import com.pega.mmock.backend.ObjectMock

class Invocation<ReturnType> {
    var args: MutableList<ArgumentsVerificationFunction> = mutableListOf()
    var objectMock: ObjectMock? = null
    var name: String? = null
    var invocationConstraint: ((Int) -> Boolean)? = null
}
