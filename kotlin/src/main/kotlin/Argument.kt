package com.bgpark

/**
 * vararg
 * - treat it as array
 * - ... in java
 * - we don't need to unwrap list
 * - only one vararg can be used in one method, it makes sense otherwise it's not possible to choose
 * @param list: array type of string
 */
fun sayHelloV1(greeting: String = "Hi", vararg list: String) {
    list.forEach { e ->
        println("$greeting $e")
    }
}

fun main() {
    sayHelloV1("Hi", "Kotlin", "Java", "Python")
    /**
     * spread operator
     * - we can pass list
    */
    val list = arrayOf("Kotlin", "Java", "Python")
    sayHelloV1("Hi", *list)

    /**
     * Named argument
     * 1. explicitly pass parameter with name
     * 2. don't need order of parameter
     * 3. readability
     * 4. enable to use default value
     */
    println("--->")
    sayHelloV1(greeting = "Hi", list = list)
    sayHelloV1(list = list)
    sayHelloV1()
}