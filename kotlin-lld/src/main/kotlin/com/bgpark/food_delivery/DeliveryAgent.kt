package com.bgpark.com.bgpark.food_delivery

data class DeliveryAgent(
    val id: String,
    val name: String,
    val phone: String
) {

    private var available: Boolean = true

    fun isAvailable(): Boolean {
        return available
    }

    fun assign() {
        available = false
    }

}
