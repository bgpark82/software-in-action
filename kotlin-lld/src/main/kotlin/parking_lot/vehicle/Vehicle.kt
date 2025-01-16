package com.bgpark.parking_lot.vehicle

abstract class Vehicle(
    open val licensePlate: String,
    open val type: VehicleType
) {

    fun isType(type: VehicleType): Boolean {
        return this.type == type
    }
}