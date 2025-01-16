package com.bgpark

fun main() {
    val set1 = emptySet<Int>()
    val set2 = setOf<Int>()
    val set3 = setOf(1,2,3)

    val set4 = mutableSetOf<Int>()
    val set5 = mutableSetOf(1,2,3)
    val set6 = buildSet {
        add(5)
        addAll(listOf(1,2,3))
    }
}