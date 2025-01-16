package com.bgpark

fun main() {
    val items = listOf("banana", "apple", "kiwi")

    for (item in items) {

    }

    for (index in items.indices) {

    }

    for ((index, item) in items.withIndex()) {

    }
    val isStart = false
    do {
        println("init")
    } while(isStart)

    // break, continue
    for (item in items) {
        if (item == "kiwi") break;
        continue
    }
}