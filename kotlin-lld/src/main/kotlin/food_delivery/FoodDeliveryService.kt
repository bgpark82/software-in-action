package com.bgpark.food_delivery

import com.bgpark.com.bgpark.food_delivery.DeliveryAgent
import com.bgpark.food_delivery.order.Order
import com.bgpark.food_delivery.order.OrderItem
import com.bgpark.food_delivery.order.OrderStatus
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class FoodDeliveryService private constructor() {

    private val customers: MutableMap<String, Customer> = ConcurrentHashMap()
    private val restaurants: MutableMap<String, Restaurant> = ConcurrentHashMap()
    private val orders: MutableMap<String, Order> = ConcurrentHashMap()
    private val agents: MutableMap<String, DeliveryAgent> = ConcurrentHashMap()


    fun registerCustomer(customer: Customer) {
        customers[customer.id] = customer
    }

    fun registerRestaurant(restaurant: Restaurant) {
        restaurants[restaurant.id] = restaurant
    }

    fun registerDeliveryAgent(agent: DeliveryAgent) {
        agents[agent.id] = agent
    }

    fun placeOrder(
        customerId: String,
        restaurantId: String,
        items: List<OrderItem>
    ): Order {
        val customer = customers[customerId]
        val restaurant = restaurants[restaurantId]

        if (customer == null) {
            throw RuntimeException("Customer is not exist : $customerId")
        }

        if (restaurant == null) {
            throw RuntimeException("Restaurant is not exist : $restaurantId")
        }

        val orderId = "ORD ${UUID.randomUUID().toString().substring(0, 8).uppercase()}"
        val order = Order(orderId, customer, restaurant)
        for (item in items) {
            order.addItems(item)
        }
        orders[order.id] = order
        notifyRestaurant(order)
        return order
    }

    fun notifyRestaurant(order: Order) {
        order.notifyRestaurant()
    }

    fun updateOrderStatus(orderId: String, confirmed: OrderStatus) {
        val order = orders[orderId]

        if (order == null) {
            throw RuntimeException("Invalid Order: ${orderId}")
        }

        order.updateStatus(confirmed)
        if (confirmed == OrderStatus.CONFIRMED) {
            assignDeliveryAgent(order)
        }

    }

    private fun assignDeliveryAgent(order: Order) {
        for (agent in agents.values) {
            if (agent.isAvailable()) {
                agent.assign()
                order.assignDelivery(agent)
            }
        }
    }

    fun cancelOrder(id: String) {
        TODO("Not yet implemented")
    }

    companion object {

        /**
         * lateinit
         * - 나중에 초기화한다
         * - 초기화 안된 상태에서 변수를 사용하면 UninitializedPropertyAccessException
         * - Non Null 타입을 강제할 수 있다
         * - 초기화가 반드시 한번 이루어져야한다
         * - nullable 타입보다 처리해야될게 많다
         */
        private lateinit var deliveryService: FoodDeliveryService

        fun getInstance(): FoodDeliveryService {
            /**
             * member reference operator
             * - 클래스, 함수, 속성 등의 멤버를 참조하는데 사용
             */
            if (!::deliveryService.isInitialized) {
                deliveryService = FoodDeliveryService()
            }
            return deliveryService
        }
    }
}