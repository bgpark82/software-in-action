package com.bgpark

class EntityV4

// extension function
fun EntityV4.info(): String {
    return "hello"
}

// extension variable
val EntityV4.info: String
    get() = "info"

fun main() {
    
}