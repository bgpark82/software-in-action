package com.bgpark.parking_lot.vehicle

class MotorCycle(
    override val licensePlate: String
): Vehicle(licensePlate, VehicleType.MOTORCYCLE) {
}