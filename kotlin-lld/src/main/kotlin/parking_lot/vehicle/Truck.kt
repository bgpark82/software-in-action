package com.bgpark.parking_lot.vehicle

class Truck(
    override val licensePlate: String
): Vehicle(licensePlate, VehicleType.TRUCK) {
}