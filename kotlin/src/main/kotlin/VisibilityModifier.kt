package com.bgpark

/**
 * internal
 * - public within module
 */
internal class PersonV4

/**
 * private
 * - only available in the file
 */
private class PersonV5

/**
 * protected
 * - within class or subclasses
 */
open class PersonV6(protected val firstName: String)

class Korean(
    firstName: String,
    val lastName: String
): PersonV6(firstName = firstName) {

    init {
        println("$firstName $lastName")
    }
}

fun main() {
    val personV6 = Korean(lastName = "Park", firstName = "Peter")
    println(personV6)
}