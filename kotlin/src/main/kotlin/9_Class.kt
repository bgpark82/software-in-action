package com.bgpark

/**
 * Class
 * - set of attributes (fields, properties, data) and method
 * - represent entity
 *
 * Abstraction
 * - how they interact each other
 * - make thing simple
 * - when drive car, we don't need to know how it works internally
 *
 * Encapsulation
 * - safety
 * - we secure the internal organs in the car
 * - public : access to anyone
 * - private : access to only in class
 * - protected : accessible in class and inheritor
 * - internal : access in module
 *
 * Inheritance
 * - Inherit the property or method of base class
 *
 * Polymorphism
 * - working with object through interface without knowing specific type
 * - Inheritors can override and change the ancestral behavior
 * - object can be used throught parents interface
 * - Liskov Substitution Principal : child class should be substitude it's parent class
 *
 * constructor(): primary constructor
 * - getter 제공
 * - setter 미제공
 * - 기본적으로 class는 public
 */
class Person
/**
 * Primary constructor
 * - constructor can be chained
 * - Order : primary constructor -> Init -> Secondary Constructor
 * - take as many as paramter as possbile and do validation at init block
 */
constructor(firstName: String) {
    val firstName: String = firstName

    /**
     * initialization block
     * - instance is run,
     * - 초기화에 init은 필요없다
     * - 여러개 정의 가능
     * - 주 생성자가 호출된 뒤 호출
     * - primary constructor -> initialization block -> secondary constructor
     * - 초기화 로직에 사용
     */
    init {
        println("Initial: $firstName")
    }

    /**
     * secondary constructor
     * - init 블록이 호출되지 않고, 보조 생성자에서 직접 초기화 로직을 작성
     */
    constructor(firstName: String, lastName: String) : this(firstName) {
        println("secondary constructor: $firstName, $lastName")
    }

    constructor(firstName: String, lastName: String, age: Int) : this(firstName, lastName) {
        println("third constructor: $firstName, $lastName, $age")
    }
}

/**
 * Define primary constructor initially
 */
class PersonV1 (
    val firstName: String,
    val lastName: String
) {
    init {
        println("init 1")
    }

    constructor() : this("Peter", "Park") {
        println("secondary constructor: $firstName, $lastName")
    }

    init {
        println("init 2")
    }
}

class PersonV3 (
    /** val : getter만 생성 */
    val firstName: String = "Peter",
    val lastName: String = "Park"
) {
    /** var : getter/setter 생성 */
    var nickName: String? = null
        set(value) {
            field = value
            println("the new nickname is $value")
        }
        get() {
            println("the returned value is $field")
            return field
        }

    var age: Int = 0

    fun printInfo() {
        //val nickNameToPrint = if (nickName != null) nickName else "no nickName"
        /**
         * Elvis operator
         */
        val nickNameToPrint = nickName ?: "no nickName"
        println("$firstName ($nickNameToPrint) $lastName")
    }

}

fun main() {
    val person = Person(firstName = "Peter", lastName = "Park", age=10)
    println(person.firstName)
    /**
     * secondary block이 호출되지 않는다
     */
    // val personV1 = PersonV1(firstName = "Peter", lastName = "Park")

    /**
     * secondary block이 호출된다
     */
    // val personV2 = PersonV1()

//    val personV3 = PersonV3()
//    personV3.firstName
//    personV3.lastName
//    personV3.nickName = "Shades"
//    personV3.nickName = "New Nickname"
//    println(personV3.nickName)
//    personV3.printInfo()

}