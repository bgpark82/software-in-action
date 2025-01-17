package com.bgpark.springjpaappv1.domain;

import jakarta.persistence.*;
import lombok.*;
import org.aspectj.weaver.ast.Or;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity
 * - JPA가 관리하는 엔티티로 선언
 * - 기본 생성자 (public, protected)
 *      - 데이터를 조회해서 엔티티 객체를 만들 때, 기본 생성자에서 Reflection로 값을 주입하여 생성
 *      - protected: 기본 생성자를 외부에서 직접 호출하지 못하게 설정
 * - final 클래스 x
 *      - JPA는 엔티티 클래스를 Proxy로 생성
 *      - Proxy는 엔티티 클래스를 상속받아 동적으로 생성
 *      - final이 있으면 상속불가
 *      - 즉, JPA는 엔티티를 Proxy로 관리, Proxy는 엔티티 클래스를 상속하여 동적으로 생성
 * - final 필드 x
 *      - JPA는 필드에 값을 Reflection으로 주입
 *      - final은 초기화 후 값을 변경할 수 없다
 */
@Entity(name = "Member")
@Table(name = "MEMBER")
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class Member {

    /**
     * @Id
     * - 직접 아이디 할당
     * @GenerateValue
     * - IDENTITY: 데이터베이스에 위임 (MySQL, Postgres)
     *      - IDENTITY는 em.persist() 시점에 즉시, INSERT 쿼리를 날려 아이디 확인 가능
     * - SEQUENCE: 시퀀스 객체 사용 (Oracle, Postgres)
     * - TABLE: 키 생성용 테이블
     * - AUTO: Dialect에 따라 자동 설정
     * - DB가 INSERT SQL를 실행한 이후 ID 확인 가능
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    /**
     * DDL
     * - DDL을 자동생성 할 때만 사용
     * - DDL 자동생성 안하면 영향없음
     * - validate로 실제 테이블과의 매핑정도만 확인하면 됨
     * name
     * - 필드 매핑할 컬럼명
     *
     */
    @Column(nullable = false, unique = false, length = 10)
    private String name;

    @Embedded
    private Address address;

    /**
     * OneToMany
     * - One이 연관관계의 주인
     * mappedBY
     * - 양방향 매핑
     * - 테이블에서는 필요없지만 객체 조회를 위해 필요
     *
     */
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    /**
     * 필드를 컬럼에 매핑 안함
     * - 즉, 테이블에는 해당 컬럼이 존재 안함
     * - 하지만 객체 레벨에서는 사용 가능
     */
    @Transient
    private String password;
}
