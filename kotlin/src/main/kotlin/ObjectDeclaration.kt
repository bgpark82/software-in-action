package com.bgpark

/**
 * object
 * - create singleton
 */
object EntityFactory {
    fun create() = EntityV1("id", "name")
}

class EntityV1 constructor(val id: String, val name: String) {

    override fun toString(): String {
        return "EntityV1(id='$id', name='$name')"
    }
}



fun main() {
    val entity = EntityFactory.create()
    print(entity)
}