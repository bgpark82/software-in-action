package com.bgpark

abstract class RegularCat {
    protected abstract val isHungry: Boolean
    private fun poop():String = "poop"
    abstract fun feed(food: String)
}

class MyCat: RegularCat() {
    override val isHungry: Boolean = false
    override fun feed(food: String) {

    }
}


fun main() {

}