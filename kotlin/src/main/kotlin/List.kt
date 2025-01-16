package com.bgpark

fun main() {
    val list1 = emptyList<Int>()
    val list2 = listOf<Int>()
    val list3 = listOf(1, 2, 3)

    val list4 = mutableListOf<Int>()
    val list5 = mutableListOf(1,2,3)
    val list6 = buildList<Int> {
        add(5)
        addAll(0, listOf(1,2,3))
    }
}