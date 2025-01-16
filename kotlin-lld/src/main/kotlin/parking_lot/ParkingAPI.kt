package com.bgpark.parking_lot

import java.util.concurrent.ConcurrentHashMap

class ParkingAPI(val parkingService: ParkingService) {

    private val MAX_RETRY = 3
    private val RETRY_DELAY = 500L
    private var CURRENT_DELAY = RETRY_DELAY
    private val cache: MutableMap<String, CacheValue> = ConcurrentHashMap()

    fun callAPI(key: String): String? {
        // takeif : 조건을 만족하면 반환 아니면 null
        if (cache.containsKey(key) && cache[key]?.isExpired() == false) {
            println("Retrieve from cache : ${cache[key]}")
            return cache[key]?.value
        }
        val res = executeWithRetry { parkingService.call() }
        res?.also { cache[key] = CacheValue(it) } // 스코프 합수 : 객체를 그대로 반환하면서 추가작업 수행, apply (객체 수정), let (결과 반환)
        return res ?: "404"
    }

    private fun <T> executeWithRetry(action: () -> T): T? {
        var retry = 0
        while (retry < MAX_RETRY) {
            try {
                return action()
            } catch (e: Exception) {
                println("Exception 발생 : $retry, 현재 딜레이: $CURRENT_DELAY")
                retry++
                if (retry >= MAX_RETRY) {
                    println("최대 retry 횟수 초과, 종료 중...")
                }
                CURRENT_DELAY = 2 * CURRENT_DELAY
                Thread.sleep(CURRENT_DELAY)
            }
        }
        return null
    }

    class CacheValue(
        val value: String,
    ) {
        private val TTL = 500
        private val timestamp = System.currentTimeMillis()

        fun isExpired(): Boolean {
            return System.currentTimeMillis() - timestamp > TTL
        }
    }
}