package com.bgpark.food_delivery

import com.bgpark.food_delivery.order.Order
import com.bgpark.food_delivery.order.OrderItem
import com.bgpark.food_delivery.order.OrderStatus

data class Restaurant(
    val id: String,
    val name: String,
    val address: String,
    val menu: MutableList<MenuItem>) {

    fun notify(customer: Customer, items: MutableList<OrderItem>, status: OrderStatus) {
        println("We've got order from customer ${customer.name}")
    }
}
