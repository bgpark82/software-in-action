package com.bgpark.food_delivery.order

import com.bgpark.food_delivery.MenuItem

data class OrderItem(
    val menuItem: MenuItem,
    val quantity: Int) {
}
