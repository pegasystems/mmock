package com.github.virelion.mmock.backend

import com.github.virelion.mmock.dsl.MMockContext

interface ObjectMock {
    val mMockContext: MMockContext
    val mocks: MockContainer
}
