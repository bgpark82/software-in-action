package com.bgpark.springjpaappv1.domain;

import jakarta.persistence.Embeddable;

/**
 * 값 타입 정의하는 곳에 표시
 * - 기본 생성자 필요
 * - Immutable
 * - 재사용
 * - 높은 응집도
 * - 의미있는 메소드 사용 가능
 */
@Embeddable
public class Address {

    private String city;
    private String street;
    private String zipcode;
}
