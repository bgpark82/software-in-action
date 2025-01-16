package com.bgpark

import java.util.UUID

object FactoryEntityV2 {

    fun create(type: EntityType): EntityV2 {
        val id = UUID.randomUUID().toString()
        val name = type.name
        val res =  when (type) {
            EntityType.EASY -> EntityV2.Easy(id, name)
            EntityType.MEDIUM -> EntityV2.Medium(id, name)
            EntityType.HARD -> EntityV2.Hard(id, name, 2f)
        }
        val res2 = when (type) {
            EntityType.EASY -> EntityV2.Easy(id, name)
            EntityType.MEDIUM -> EntityV2.Medium(id, name)
            EntityType.HARD -> EntityV2.Hard(id, name, 0.1f)
        }
        return res2;
    }
}

/**
 * sealed
 * - inherit within file
 * - like enum
 * - but different properties and method
 * - ADT(algebraic Data Types)
 * - enum과의 차이점
 *  - 각 타입마다 다른 property와 method를 가질 수 있다
 *  - 컴파일러가 smart casting으로 property와 method를 사용할 수 있다
 */
sealed class EntityV2() {
    // object 클레스 사용 가능
    object Help: EntityV2() {
        val name = "HELP"
    }

    // data 클래스 사용 가능
    data class Easy(val id: String, val name:String): EntityV2() {}
    data class Medium(val id: String, val name:String): EntityV2()
    data class Hard(val id: String, val name:String, val multipier: Float): EntityV2()
}

fun main() {
    val entity: EntityV2 = FactoryEntityV2.create(EntityType.EASY)
    val msg = when (entity) {
        is EntityV2.Easy -> "easy"
        is EntityV2.Hard -> "Hard"
        EntityV2.Help -> "Help"
        is EntityV2.Medium -> "Medium"
    }

    val entity1: EntityV2 = FactoryEntityV2.create(EntityType.EASY)
    val entity2: EntityV2 = FactoryEntityV2.create(EntityType.EASY)
    if (entity1 == entity2) {
        println("true")
    } else {
        println("false")
    }

    val entity3 = EntityV2.Easy("id", "name")
    val entity4 = entity3.copy()
    println(entity3 == entity4)
    println("refrential equality = ${entity3 === entity4}")
    val entity5 = entity3.copy(name = "name1")
    println(entity3 == entity5)
    println("refrential equality = ${entity3 === entity5}")
}