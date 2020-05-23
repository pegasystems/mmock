package com.github.virelion.mmock.demo

import com.github.virelion.mmock.GenerateMock
import com.github.virelion.mmock.TypeParameter

@GenerateMock
interface ExampleInterface : BaseInterface {
    val property: BaseInterface?
    var mutableProperty: BaseInterface?

    fun simple()
    fun function(arg: Int): Int?
    suspend fun suspendFunction(arg: Int?): Int?
    fun <T> genericFunction(arg: @TypeParameter("T") T): @TypeParameter("T") T
    fun <A, B, C> complicatedGenericFunction(map: Map<@TypeParameter("A") A, @TypeParameter("kotlin.collections.List<out kotlin.collections.List<C>>")List<out List<C>>>)
    fun functionWithTypeOutsideOfProject(arg: BaseInterface): BaseInterface
}
