/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.backend

import com.pega.mmock.RecordingDoneMarker
import com.pega.mmock.backend.stack.Invocation
import com.pega.mmock.backend.stack.MethodElement
import com.pega.mmock.backend.stack.StackElement
import com.pega.mmock.dsl.RecordingContext

internal class RecordingContextImpl : RecordingContext {
    enum class State {
        RECORDING, INVOKING
    }

    var state: State = State.INVOKING
        private set

    override var recordingStack: MutableList<StackElement>? = null

    override suspend fun <R> record(block: suspend RecordingContext.() -> R): List<Invocation<R>> {
        try {
            state = State.RECORDING
            val finalEventStack = mutableListOf<StackElement>()
            recordingStack = finalEventStack
            try {
                block()
            } catch (e: RecordingDoneMarker) {
                // do nothing
            }
            recordingStack = null

            val invocations = mutableListOf<Invocation<R>>()
            var invocation = Invocation<R>()

            finalEventStack.forEach {
                it.visit(invocation, invocations)
                if (it is MethodElement) {
                    invocations.add(invocation)
                    invocation = Invocation()
                }
            }

            return invocations
        } finally {
            state = State.INVOKING
            recordingStack = null
        }
    }
}
