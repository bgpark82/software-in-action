package parking_lot

import com.bgpark.parking_lot.ParkingSpot
import com.bgpark.parking_lot.vehicle.VehicleType
import org.junit.jupiter.api.Assertions.assertNull

class ParkingSpotTest {

    @org.junit.jupiter.api.Test
    fun setter() {
        val spot = ParkingSpot(1, VehicleType.CAR)
        // spot.parkedVehicle = Car("123")
        println("parked vehicle: ${spot.parkedVehicle}")
        assertNull(spot.parkedVehicle)
    }
}