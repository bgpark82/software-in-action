package com.bgpark.food_delivery.order

import com.bgpark.com.bgpark.food_delivery.DeliveryAgent
import com.bgpark.food_delivery.Customer
import com.bgpark.food_delivery.Restaurant

data class Order(
    val id: String,
    val customer: Customer,
    val restaurant: Restaurant
) {

    val items: MutableList<OrderItem> = mutableListOf()
    var status: OrderStatus = OrderStatus.PENDING
    lateinit var agent: DeliveryAgent


    fun addItems(item: OrderItem) {
        items.add(item)
    }

    fun notifyRestaurant() {
        restaurant.notify(customer, items, status)
    }

    fun updateStatus(status: OrderStatus) {
        this.status = status
    }

    fun assignDelivery(agent: DeliveryAgent) {
        this.agent = agent
    }

}