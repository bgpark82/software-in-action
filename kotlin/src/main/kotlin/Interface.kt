package com.bgpark

interface Shape {
    val info: String

    fun print(person: Person) {
        println("Interface can provide default functionality $info")
    }
}

interface Color {
    fun printColor(): String
}

/**
 * abstract
 * - use to define abstract class or member
 * - you must redefine in subclass
 * - you must not provide default functionality
 * open
 * - explicity define class and member can be redefined
 * - class and member in kotlin is final, so we need to declare open to redefine
 * - provide default functionality
 */
abstract class Circle : Shape, Color {
    abstract fun draw()
    open fun resize() { // provide default function
        println("resizing shape")
    }
    fun info() { // can't override
        println("this is the shape")
    }
}

open class ColoredCircle(
    override val info: String // we need to override interface info
): Circle() {

//    override val info: String
//        get() = "Basic"

    override fun draw() {
        println("it can be redefine")
    }

    override fun print(person: Person) {
        println("it can be redefine")
    }

    override fun printColor(): String {
        return "it can be redefine"
    }

    override fun resize() {
        println("it can be redefine")
    }

// we can't redefined
//    fun info() { }
}

class ColorFillCircle: ColoredCircle("info") {

    override val info: String
        get() = "Color Fill Circle"

    override fun print(person: Person) {
        super.print(person)
        println(person.firstName)
    }
}

fun main() {
    checkType(ColoredCircle("info"))
    val colorFillCircle = ColorFillCircle()
    println(colorFillCircle.info)
    colorFillCircle.print(Person("first","second"))
}

fun checkType(shape: Shape) {
    if (shape !is Circle) {
        println("is not a circle")
    } else {
        println("is a circle")
        // smart casting
        // compile type and type is not change don't need to do any casting
        // (shape as Circle).draw()
        shape.draw()
    }
}
