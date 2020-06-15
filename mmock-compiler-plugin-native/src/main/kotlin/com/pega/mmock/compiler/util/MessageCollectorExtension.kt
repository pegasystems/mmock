/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.compiler.util

import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.MessageCollector

fun MessageCollector.p(message: Any?) {
    report(CompilerMessageSeverity.WARNING, "[${System.nanoTime()}] $message")
    Thread.sleep(1L)
}

fun <T> MessageCollector.report(label: String, iterable: Iterable<T>, action: MessageCollector.(T) -> Unit) {
    p("<$label count='${iterable.count()}' >")
    iterable.forEach {
        action(it)
    }
    p("</$label>")
}

fun <T> MessageCollector.report(label: String, array: Array<out T>, action: MessageCollector.(T) -> Unit) {
    p("<$label count='${array.size}' >")
    array.forEach {
        action(it)
    }
    p("</$label>")
}
