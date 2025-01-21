package com.bgpark.code_review;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * 코드 리뷰 가이드라인
 * https://medium.com/@AlexanderObregon/best-practices-for-java-code-reviews-6ab74f603803
 * - 코드 퀄리티
 * - 버그를 빨리 찾는다
 * - Knowledge Sharing
 * 체크 포인트
 * - Risk Mitigation
 * - Performance Optimization
 * - Best Practice
 *
 */
public class CodeReviewBestPractice {

    @Test
    void lambda() {
        List<Integer> oddNumbers = new ArrayList<>();
        for (Integer number : Arrays.asList(1,2,3,4,5)) {
            if (number % 2 == 1) {
                oddNumbers.add(number);
            }
        }
        assertThat(oddNumbers).contains(1, 3, 5);

        List<Integer> oddNumbersV2 = Stream.of(1, 2, 3, 4, 5)
                .filter(number -> number % 2 == 1)
                .collect(Collectors.toList());

        assertThat(oddNumbersV2).contains(1, 3, 5);
    }

    @Test
    void should_use_optional_instead_of_null_point_exception() {
        List<Integer> items = Collections.emptyList();
        highestV2(items).ifPresent(number -> {
            boolean isEven = number % 2 == 0;
        });
    }

    @Test
    void do_not_assign_reference_from_client_code() {
        List<Integer> origin = Arrays.asList(1, 2, 3);
        List<Integer> update = new ArrayList<>(origin);
        update.add(4);
        assertThat(origin.size()).isEqualTo(3);
        assertThat(update.size()).isEqualTo(4);
    }

    /**
     *
     * 예외처리
     * 1. 예외 처리 순서
     *  - 자바 클래스는 예외 클래스가 계층 구조를 가짐
     *  - 상위 계층의 예외가 발생하면 그것 먼저 실행
     * 2. 예외 메세지
     *  - e.printStackTrace() // 운영 서버는 비추천: 1. 표준출력(stderr), 로그 레벨, 성능 (IO),
     *  - 예외를 삼키지 않는다
     * 3. 사용자 정의 예외
     * 4. 자원을 안전하게 해제
     *  - try with resource
     */
    @Test
    void sequence_of_exception_from_specific_to_least() {
        Stack<Integer> stack = new Stack<>();
        try {
            stack.pop();
        } catch (RuntimeException exception) {

        } catch (Exception exception) {

        }
    }

    private Integer highestV1(List<Integer> items) {
        if (items.isEmpty()) {
            return null;
        }
        Integer highest = null;
        for (Integer item : items) {
            if (items.indexOf(item) == 0) {
                highest = item;
            } else {
                highest = highest > item ? highest : item;
            }
        }
        return highest;
    }

    private Optional<Integer> highestV2(List<Integer> items) {
        return items.stream()
                .reduce((num1, num2) -> num1 > num2 ? num1 : num2);
    }
}
