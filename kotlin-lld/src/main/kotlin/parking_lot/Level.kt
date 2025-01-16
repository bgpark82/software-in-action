package com.bgpark.parking_lot

import com.bgpark.parking_lot.vehicle.Vehicle
import com.bgpark.parking_lot.vehicle.VehicleType

/**
 * @property floor: floor of parking lot
 * @property parkingSpots: number of parking spot in floor == numSpots
 */
class Level(
    val floor: Int,
    /**
     * val가 없으면 "로컬 변수"로 동작한다.
     * - val은 final, getter만 생성한다
     * - val가 없으면 내부에서만 사용할 수 있다
     */
    numSpots: Int
) {
    private val parkingSpots: MutableList<ParkingSpot?>

    /**
     * init 블록
     * - 생성자 실행 이후 초기화에 실행
     * - primary constructor -> init -> secondary constructor
     */
    init {
        /**
         * 코틀린은 nullsafe하다
         * list를 생성할 때 기본적으로 사이즈를 지정한다.
         * 사이즈를 지정하면 내부적으로 사이즈만큼 null을 채워넣거나 기본값을 넣는다
         */
        parkingSpots = MutableList(numSpots) { null }

        // numSpot이 10인 경우, bike = 5, car = 4, truck = 1
        // val numBike = (numSpots * 0.50).toInt() // 5
        // val numCars = (numSpots * 0.40).toInt() // 4
        // val numTrucks = (numSpots * 0.10).toInt() // 1

        val distribution = mapOf(
            VehicleType.MOTORCYCLE to 0.50,
            VehicleType.CAR to 0.40,
            VehicleType.TRUCK to 0.10
        )

        var currentIndex = 1
        distribution.forEach { (vehicleType, ratio) ->
            val count = (numSpots * ratio).toInt()
            repeat(count) {
                parkingSpots.add(ParkingSpot(currentIndex++, vehicleType))
            }
        }
    }

    fun parkVehicle(vehicle: Vehicle): Boolean {
//        parkingSpots.firstOrNull { spot ->
//            spot?.isAvailable() == true && spot.isType(vehicle.type)
//        }?.let { spot -> // null 객체를 안전하게 처리, 스코프 제한에서 처리, null이면 실행 안되
//            spot.parkVehicle(vehicle)
//            return true
//        }
//        return false
        for (spot in parkingSpots) {
            if (spot?.isAvailable() == true && spot.isType(vehicle.type)) {
                spot.parkVehicle(vehicle)
                return true
            }
        }
        return false
    }

    fun unparkVehicle(vehicle: Vehicle): Boolean {

        for (spot in parkingSpots) {
            spot?.parkedVehicle == null
            if (spot?.isAvailable() == false && spot.parkedVehicle == vehicle) {
                spot.unparkVehicle()
                return true
            }
        }
        return false
    }

    fun displayAvailability() {
        for (spot in parkingSpots) {
            System.out.println(("Spot ${spot?.spotNumber} : ${if (spot!!.isAvailable()) "Available For" else "Occupied By "} ${spot.vehicleType}"))
        }
    }
}
