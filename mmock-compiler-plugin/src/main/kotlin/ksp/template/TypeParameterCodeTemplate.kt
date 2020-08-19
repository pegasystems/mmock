/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package ksp.template

import org.jetbrains.kotlin.ksp.symbol.Variance

class TypeParameterCodeTemplate(
    val name: String,
    val variance: Variance
) {
    override fun toString(): String {
        return (getVarianceLabel() + " " + name).trim()
    }

    private fun getVarianceLabel(): String {
        return when(variance) {
            Variance.COVARIANT -> "out"
            Variance.CONTRAVARIANT -> "in"
            else -> variance.label
        }
    }
}
