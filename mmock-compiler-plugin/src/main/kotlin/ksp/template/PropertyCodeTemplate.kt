/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package ksp.template

import ksp.utils.CodeBuilder

internal class PropertyCodeTemplate(
    val name: String,
    val type: String,
    val mutable: Boolean
) : CodeTemplate {
    private val tag: String = if (mutable) "var" else "val"

    override fun generate(builder: CodeBuilder): String {
        return builder.build {
            appendln("override $tag $name: $type")
            indent {
                appendln("get() { return mocks.invoke(\"`property`(GET, $name)\") }")
                if (mutable) {
                    appendln("set(value) { return mocks.invoke(\"`property`(SET, $name)\", value) }")
                }
            }
        }
    }
}
