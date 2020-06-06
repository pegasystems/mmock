package com.github.virelion.mmock.demo

import com.github.virelion.mmock.GenerateMock

@GenerateMock
interface ExampleInterface : BaseInterface {
    val property: BaseInterface?
    var mutableProperty: BaseInterface?

    fun simple()
    fun function(arg: Int): Int?
    suspend fun suspendFunction(arg: Int?): Int?
    fun <T> genericFunction(arg: T): T
    fun <A, B, C> complicatedGenericFunction(map: Map<A, List<out List<C>>>)
    fun withAsterix(list: List<*>): List<*>
    fun functionWithTypeOutsideOfProject(arg: BaseInterface): BaseInterface
}
