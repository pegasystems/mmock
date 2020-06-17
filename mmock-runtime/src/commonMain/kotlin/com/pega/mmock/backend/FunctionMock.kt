/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.backend

open class FunctionMock<R>(
    verificationFunction: ArgumentsConstraints,
    private val result: R
) : FunctionMockBase<R>(verificationFunction) {
    override fun invoke(): R {
        return result
    }
}
