package parking_lot

import com.bgpark.parking_lot.ParkingAPI
import com.bgpark.parking_lot.ParkingService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ParkingAPITest {

    @Test
    fun success() {
        val api = ParkingAPI(createService())
        assertEquals(api.callAPI("key"), "200")
        assertEquals(api.callAPI("key"), "200")
    }

    @Test
    fun retry() {
        val api = ParkingAPI(createFailService())
        assertEquals(api.callAPI("key"), "404")
    }

    @Test
    fun eviction() {
        val api = ParkingAPI(createService())
        assertEquals(api.callAPI("key"), "200")
        Thread.sleep(1000)
        // 캐시는 500ms 이후에 만료됨
        // 1초 뒤에는 캐시가 만료되어 있을 것이므로 캐시값을 사용하지 않음
        assertEquals(api.callAPI("key"), "200")
    }

    @Test
    fun eviction2() {
        val api = ParkingAPI(createService())
        assertEquals(api.callAPI("key"), "200")
        Thread.sleep(200)
        // 캐시는 500ms 이후에 만료됨
        // 200ms 뒤에는 캐시가 만료되지 않을 것이므로 캐시값을 사용하지 않음
        assertEquals(api.callAPI("key"), "200")
    }

    fun createService(): ParkingService {
        return object : ParkingService {
            override fun call(): String {
                return "200"
            }
        }
    }

    fun createFailService(): ParkingService {
        return object : ParkingService {
            override fun call(): String {
                throw RuntimeException("API 예외 발생")
            }
        }
    }
}