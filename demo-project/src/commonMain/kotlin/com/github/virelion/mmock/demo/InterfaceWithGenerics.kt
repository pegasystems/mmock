package com.github.virelion.mmock.demo

import com.github.virelion.mmock.GenerateMock

@GenerateMock
interface InterfaceWithGenerics<in T, out R, V> : BaseInterface {
    val property: R
    var mutableProperty: V

    fun function(input: T): R
}
