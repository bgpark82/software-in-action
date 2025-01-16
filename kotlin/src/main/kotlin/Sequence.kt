package com.bgpark

/**
 * List
 * - eager evaluation:
 * - 연산이 실행되는 즉시 모든 작업 수행
 * - 결과를 메모리에 저장
 * - 중간 결과를 저장
 * - 크기가 고정된 데이터를 처리, 혹은 여러번 순회가 필요한 경우 적합
 * - map, filter를 적용하면 항상 새로운 리스트가 생성
 *
 * Sequence
 * - lazy evaluation
 * - 필요한 시점에 연산을 수행
 * - 중간 연산 (map, filter) 연산은 미루고 최정 연산을 호출할 때만 실제 수행
 * - 큰 데이터 처리 및 효율적인 연산에 적합
 * - 데이터를 순회하며 즉시 반환
 *
 */
fun main() {
    val words = "The quick brown fox jumps over the lazy dog".split(" ") // Returns a list
    val lengths = words
        .filter { println("filter: $it"); it.length > 3 }
        .map { println("length: ${it.length}"); it.length }
        .take(4)
    println(lengths)
    println("----")

    val wordSeq = words.asSequence()
    val lengthsSeq = wordSeq
        .filter { println("filter: $it"); it.length > 3 }
        .map { println("length: ${it.length}"); it.length }
        .take(4)
    println(lengthsSeq.toList())

    val nums = listOf(1,2,3,4,5,6)
    nums.chunked(2) // [1,2] [3,4] [5,6]
    nums.chunked(2) { it.sum() } // [[1,2] [3,4] [5,6]] -> [3, 7, 11]
    nums.windowed(2) // [[1,2] [2,3] [3,4]...]

    /**
     * Immutable sort
     * - immutable 컬렉션은 sort 이후 새로운 객체를 반환
      */
    println("sorted: ${nums.sorted()}")
    println("sortedDescending: ${nums.sortedDescending()}")
    println("sortedBy: ${nums.sortedBy { it.dec() }}")
    println("sortedByDescending: ${nums.sortedByDescending { it.dec() }}")
    println("sortedWith: ${nums.sortedWith(compareBy<Int> { it.dec() }.thenBy { it })}")

    /**
     * mutable sort
     * - mutable 컬렉션은 sort 이후 새로운 객체를 반환하지 않는다 (자바와 유사)
     */
}