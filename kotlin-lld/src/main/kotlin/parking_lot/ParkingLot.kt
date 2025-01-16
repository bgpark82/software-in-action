package com.bgpark.parking_lot

import com.bgpark.parking_lot.vehicle.Vehicle

/**
 * object
 */
class ParkingLot private constructor(
){
    private val levels: MutableList<Level> = mutableListOf()

    fun addLevel(level: Level) {
        levels.add(level)
    }

    fun parkVehicle(vehicle: Vehicle): Boolean {
        for (level in levels) {
            if (level.parkVehicle(vehicle)) {
                println("vehicle parked successfully")
                return true
            }
        }
        println("Could not park vehicle")
        return false
    }

    fun displayAvailability() {
        for (level in levels) {
            level.displayAvailability()
        }
    }

    fun unParkVehicle(vehicle: Vehicle): Boolean {
        for (level in levels) {
            if (level.unparkVehicle(vehicle)) {
                return true;
            }
        }
        return false
    }

    companion object {
        private val INSTANCE: ParkingLot = ParkingLot()

        fun getInstance(): ParkingLot {
            return INSTANCE
        }
    }
}