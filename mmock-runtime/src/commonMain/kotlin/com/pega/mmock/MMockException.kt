/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock

sealed class MMockException(message: String?) : RuntimeException(message)

class MMockRecordingException(message: String) : MMockException(message)
class MMockVerificationException(message: String) : MMockException(message)
class NoMethodStubException(message: String? = null) : MMockException(message)
class MMockStubbingException(message: String?) : MMockException(message)
