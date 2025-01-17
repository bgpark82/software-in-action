package com.bgpark.springjpaappv1.domain;

import jakarta.persistence.MappedSuperclass;

/**
 * 공통 매핑 정보가 필요할 때 사용
 * - 필드만 추가한다
 * - 조회, 검색 불가 (em.find(BaseEntity) 이런 형식으로만 불가)
 * - e.g. createdAt, updatedAt
 */
@MappedSuperclass
abstract public class EntityMapper {
}
