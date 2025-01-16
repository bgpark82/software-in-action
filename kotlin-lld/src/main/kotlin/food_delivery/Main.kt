package com.bgpark.food_delivery

import com.bgpark.com.bgpark.food_delivery.DeliveryAgent
import com.bgpark.food_delivery.order.OrderItem
import com.bgpark.food_delivery.order.OrderStatus

fun main() {
    val deliveryService = FoodDeliveryService.getInstance()

    val customer1 = Customer(id = "C001", name = "John Doe", email = "john@example.com", phone = "123455")
    val customer2 = Customer(id = "C002", name = "Jane Smith", email = "jane@example.com", phone = "123455")
    deliveryService.registerCustomer(customer1)
    deliveryService.registerCustomer(customer2)

    // 식당 등록
    val restaurantMenu1 = mutableListOf<MenuItem>(
        MenuItem("M001", "Burger", "Delicious burger", 9.99),
        MenuItem("M002", "Pizza", "Delicious pizza", 12.99)
    )
    val restaurant1 = Restaurant("R001", "Restaurant 1", "Address 1", restaurantMenu1)
    deliveryService.registerRestaurant(restaurant1)

    val restaurantMenu2 = mutableListOf<MenuItem>(
        MenuItem("M003", "Sushi", "Delicious sushi", 9.99),
        MenuItem("M004", "Ramen", "Delicious ramen", 12.99)
    )
    val restaurant2 = Restaurant("R002", "Restaurant 2", "Address 2", restaurantMenu2)
    deliveryService.registerRestaurant(restaurant2)

    // 배달
    val agent1 = DeliveryAgent("D001", "Agent 1", "99999999")
    val agent2 = DeliveryAgent("D002", "Agent 2", "88888888")
    deliveryService.registerDeliveryAgent(agent1)
    deliveryService.registerDeliveryAgent(agent2)

    // order 1
    val orderItems1 = mutableListOf(
        OrderItem(restaurantMenu1[0], 2),
        OrderItem(restaurantMenu1[1], 2)
    )
    val order1 = deliveryService.placeOrder(customer1.id, restaurant1.id, orderItems1)
    deliveryService.updateOrderStatus(order1.id, OrderStatus.CONFIRMED)

    // order 2
    val orderItems2 = mutableListOf(
        OrderItem(restaurantMenu2[0], 1),
        OrderItem(restaurantMenu2[1], 2)
    )
    val order2 = deliveryService.placeOrder(customer2.id, restaurant2.id, orderItems2)
    deliveryService.cancelOrder(order2.id)
}