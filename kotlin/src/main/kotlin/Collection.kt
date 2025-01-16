package com.bgpark

fun sayHello(greeting: String, list: Array<String>) {
    list.forEach { e ->
        println("$greeting $e")
    }
}

fun main() {
    /**
     * List
     * listOf = immutable
     * mutableListOf = mutable
     */
    val listV1 = arrayOf("Kotlin", "Programming")
    val listV2 = mutableListOf("Kotlin", "Programming")
    listV2.add("Java")

    listV2.forEach { element ->
        println(element);
    }

    sayHello(greeting = "hello", list = listV1)

    /**
     * Map
     * mapOf = immutable
     * mutableMapOf = mutable
     */
    val mapV1 = mapOf(1 to "a", 2 to "b")
    val mapV2 = mutableMapOf(1 to "a", 2 to "b")
    mapV2.put(3, "c")
    mapV2.forEach { key, value ->
        println("$key -> $value")
    }

    /**
     * Loop
     * - until : 100 not included
     * - .. : 100 included
     * - in : collection based
     */
    for (i in 0 until 100) { }
    for (i in 0 .. 100) {}
    for (i in listV1) {}
}