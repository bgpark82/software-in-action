package com.bgpark.springjpaappv1.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
/**
 * 상속 관계 매핑
 * - RDB는 상속관계 없음
 * - 슈퍼타입, 서브타입이 상속과 유사
 * - 객체 상속을 RDB의 슈퍼타입, 서브타입 관계로 매핑
 * 전략
 * - JOINED: 각각의 테이블
 *      - 장점: 테이블 정규화, 외래키, 저장공간
 *      - 단점: 조인 많음, 복잡한 쿼리, INSERT 두번 호출
 * - SINGLE_TABLE: 통합 테이블
 *      - 장점: 조인없음, 빠른 성능, 단순한 쿼리
 *      - 단점: null 허용, 큰 테이블
 * - TABLE_PER_CLASS: 서브타입 테이블
 *      - 비추천
 */
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
/**
 *
 */
@DiscriminatorColumn(name = "dtype")
@Getter
@Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();
}
