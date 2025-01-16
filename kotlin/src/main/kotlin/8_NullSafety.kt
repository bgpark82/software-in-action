package com.bgpark

fun main() {
    val notNullText: String = "Definitely Not Null"
    val nullableText: String? = "Might be null"
    val nullableText2: String? = null

    /**
     * Elvis operator
     * - expression if left of ?: is not null return the value
     */
    val item = generator(null) ?: null
    val item2 = generator("null") ?: throw Exception("...")

    /**
     * Safe call
     * - ?.
     * - can be used with scope function
     */
    val people = People(Department(null))
    people?.department?.head?.name?.let {
        println(it)
    }

    /**
     * UnSafe call
     * - !!
     * - NPE
     */
    people.department?.head!!.let {
        println(it)
    }

    /**
     * TODO
     * - throw NotImplementedError at runtime
     */
    TODO("")
}

fun generator(str: String?): String? {
    return str
}

class Head(
    val name: String?
)
class Department(
    val head: Head?
)
class People (
    val department: Department?
) {

}