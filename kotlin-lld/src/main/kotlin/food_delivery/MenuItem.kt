package com.bgpark.food_delivery

data class MenuItem(
    private val id: String,
    private val name: String,
    private val description: String,
    private val price: Double,
) {
    private val available: Boolean = true



}
