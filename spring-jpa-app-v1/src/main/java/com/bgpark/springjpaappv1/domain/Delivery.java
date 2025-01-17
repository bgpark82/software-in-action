package com.bgpark.springjpaappv1.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    /**
     * Embedded 자체가 null이면 해당 필드도 null
     */
    @Embedded
    private Address address;

    /**
     * DDL
     * - enum 타입 매핑
     * - ORDINAL: enum '순서'를 저장
     * - STRING: enum '이름'을 저장
     */
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
}
