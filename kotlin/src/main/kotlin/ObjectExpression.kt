package com.bgpark



fun main() {
    val square = object : Shape {
        override val info: String
            get() = "Square"

        fun size() = "10"
    }
}