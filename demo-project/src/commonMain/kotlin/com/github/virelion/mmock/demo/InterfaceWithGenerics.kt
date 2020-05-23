package com.github.virelion.mmock.demo

import com.github.virelion.mmock.GenerateMock
import com.github.virelion.mmock.TypeParameter

@GenerateMock
interface InterfaceWithGenerics<in T, out R, V> : BaseInterface {
    val property: @TypeParameter("R") R
    var mutableProperty: @TypeParameter("V") V

    fun function(input: @TypeParameter("T") T): @TypeParameter("R") R
}
