package com.bgpark

interface IdProvider {
    fun getId(): String
}

class Entity private constructor(val id: String) {

    companion object Factory: IdProvider {
        override fun getId(): String {
            return "123"
        }

        const val CONSTANT = "id"
        fun create() = Entity(CONSTANT)
    }
}

fun main() {
    val entity = Entity.Factory.create()
    Entity.CONSTANT
}