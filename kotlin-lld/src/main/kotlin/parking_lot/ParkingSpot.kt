package com.bgpark.parking_lot

import com.bgpark.parking_lot.vehicle.Vehicle
import com.bgpark.parking_lot.vehicle.VehicleType

/**
 * @property spotNumber: number of spot
 * @property vehicleType: type of vehicle
 * @property parkedVehicle: parked vehicle
 */
class ParkingSpot(
    val spotNumber: Int,
    val vehicleType: VehicleType,
) {
    var parkedVehicle: Vehicle? = null // 로컬변수, 클래스 내부의 다른 메서드나 프로퍼티에서 접근 불가능
        private set

    /**
     * backing field
     * - 프로퍼티 값을 저장하는 실제 저장소
     * - 컴파일러가 필요할 때 자동으로 생성,
     * 사용 시간
     * - getter와 setter를 직접 정의해야 할 때 사용
     * - setter로 지연 초기화 할 때 사용 (lazy loading)
     */
    // private var parkedVehicle: Vehicle?

    fun isAvailable(): Boolean {
        return parkedVehicle == null
    }

    fun isType(type: VehicleType): Boolean {
        return vehicleType == type
    }

    fun parkVehicle(vehicle: Vehicle) {
        checkAvailable(vehicle)

        parkedVehicle = vehicle
    }

    fun unparkVehicle() {
        parkedVehicle = null
    }

    private fun checkAvailable(vehicle: Vehicle) {
        if (!isAvailable() && !vehicle.isType(vehicleType)) {
            throw IllegalArgumentException("Invalid vehicle type or spot already occupied")
        }
    }
}