package com.bgpark

/**
 * when
 * - evaluate the first suitable branch
 * - as soon as find the first branch condition then return
 * - it works like consecutive if-else block
 * - else can be omitted when block is used as a statement
 */
fun main(): Unit {
    /**
     * Number
     * - super abstract class to represent number data
     */
    val res = when (15 as Number) {
        1 -> "1"
        /**
         * in
         * - check range or collection
         */
        in listOf(2,3,4,5,6) -> "2"
        is Double -> "double"
        else -> "null"
    }
    val x = 10
    val res2 = if (x == 1) "1"
        else if (x in listOf(2, 3, 4, 5, 6)) "2"
        else if (x is Int) "3"
        else "null"
    println(res)

    // range
    println(discountRange(400))

    // statement
    println(state("val"))
}

fun discountRange(cost: Int): Int {
    return when (cost) {
        in 0 until 300 -> 0
        in 300 until 1500 -> 5
        else -> 15
    }
}

fun state(value: Any) {
    when (value) {
        is String -> error("String")
        is Int, Double -> error ("String")
    }
}