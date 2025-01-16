package com.bgpark.com.bgpark.food_delivery

data class Restaurant(
    val id: String,
    val name: String,
    val address: String,
    val menu: MutableList<MenuItem>) {

}
