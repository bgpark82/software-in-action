package com.bgpark.parking_lot.vehicle

class Car(
    override val licensePlate: String
): Vehicle(licensePlate, VehicleType.CAR) {
}