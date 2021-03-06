/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.backend.unsafe

actual inline fun <reified T> createUnsafe(): T {
    // This should not be implicitly cast
    // Implicit cast will always fail
    return js("undefined")
}
