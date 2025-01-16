package com.bgpark.parking_lot

import com.bgpark.parking_lot.vehicle.Car
import com.bgpark.parking_lot.vehicle.MotorCycle
import com.bgpark.parking_lot.vehicle.Truck

fun main() {
    val parkingLot = ParkingLot.getInstance()
    parkingLot.addLevel(Level(1, 100))
    parkingLot.addLevel(Level(2, 80))

    val car = Car("ABC")
    val truck = Truck("XYW")
    val motorcycle = MotorCycle("M123")

    parkingLot.parkVehicle(car)
    parkingLot.parkVehicle(truck)
    parkingLot.parkVehicle(motorcycle)

    parkingLot.displayAvailability()

    parkingLot.unParkVehicle(motorcycle)

    parkingLot.displayAvailability()



}