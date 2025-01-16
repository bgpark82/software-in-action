package com.bgpark

/**
 * Exception
 * - signal that something is exceptionally wrong
 *  - development mistake
 *  - system errors
 *  - errors
 *
 * Do not use exception
 * - Control flow (흐름제어: 흐름 제어용으로 exception을 남기는 것은 좋지 않다)
 * - Manageable errors (관리 가능한 오류: 예외적인 상황이 아니다. 안전하게 변환
 * - ex) 입력 검증 -> boolean으로 처리,데이터 조회 실패, 상태확인 -> 모두 null이나 기본값을 리턴
 */
fun main() {
    try {
        throw Exception("An exception", RuntimeException("A cause"))
    } catch (e: Exception) {
        println("Message: ${e.message}")
        println("Cause: ${e.cause}")
        println("Exception: ${e}")
        e.printStackTrace()
    } finally {
        println("Finally")
    }

    /**
     * Kotlin sugar
     * - require() -> IllegalArgumentException
     * - error() -> IllegalStateException
     */
    require(4 > 10) { "Not allow negative" }
    error("ErrorMessage")
}

/**
 *              Throwable
 *      Error               Exception
 *                      RuntimeException (No CheckedException!)
 */