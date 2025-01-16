package com.bgpark

import java.util.*

enum class EntityType {
    EASY, MEDIUM, HARD;

    fun getFormattedName(): String = name.toLowerCase().capitalize()
}

object EntityFactoryV1 {
    fun create(type: EntityType) : EntityV1 {
        val id = UUID.randomUUID().toString()
//        val name = when(type) {
//            EntityType.EASY -> "Easy"
//            EntityType.MEDIUM -> "MEDIUM"
//            EntityType.HARD -> "HARD"
//        }
        val name = type.getFormattedName()
        return EntityV1(id, name)
    }
}

fun main() {
    val entity = EntityFactoryV1.create(EntityType.EASY)
    println(entity)

}