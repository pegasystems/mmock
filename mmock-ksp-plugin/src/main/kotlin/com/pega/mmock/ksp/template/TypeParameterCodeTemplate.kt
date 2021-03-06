/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.ksp.template

import com.google.devtools.ksp.symbol.Variance

class TypeParameterCodeTemplate(
    val name: String,
    val variance: Variance
) {
    override fun toString(): String {
        return (variance.label + " " + name).trim()
    }
}
