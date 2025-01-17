package com.bgpark.springjpaappv1.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    /**
     * 테이블과 객체를 매핑
     * - 테이블: 외래키로 관계
     * - 객체: 참조로 관계
     */
    @ManyToOne(fetch = FetchType.LAZY)
    /**
     * 조인 컬럼
     * - 외래키 연결할 때 사용
     * - 조인 컬럼이 없으면 조인 테이블을 사용
     *      - 테이블 사이에 테이블 추가로 생성하여 관리
     * - 테이블(외래키)와 객체(참조)를 설정
     */
    @JoinColumn(name = "member_id")
    private Member member;

    /**
     * cascade
     * - 특정 엔티티를 영속상태로 만들 때, 연관된 엔티티도 영속 상태로 만들때
     * - 연관관계 매핑과 아무런 상관없음, 편의성 제공
     * ALL: 모두
     * PERSIST: 영속
     * - Order가 저장되면 OrderItems도 저장
     * REMOVE: 삭제
     * - 부모 엔티티가 삭제될 때 자식 엔티티도 삭제
     * MERGE: 병합
     * DETACH: 준영속
     *
     * orphanRemoval
     * - 부모 엔티티가 자식과의 관계를 끊을 때 자식을 제거
     * - cascade.REMOVE 차이: 부모는 살아있음
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
