package com.bgpark.springjpaappv1.learning;

import com.bgpark.springjpaappv1.domain.EntityMapper;
import com.bgpark.springjpaappv1.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceUnitUtil;
import org.hibernate.jpa.internal.PersistenceUnitUtilImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ProxyTest {

    @Autowired
    private EntityManager em;

    @Test
    void 프록시() {
        Member member = new Member();
        member.setName("회원1");

        em.persist(member);

        System.out.println(member.getClass().getName());
        System.out.println(em.getReference(Member.class, 1L));

        // 1차 캐시에 저장된 엔티티 반환
        Member findMember = em.find(Member.class, 1L);
        System.out.println(findMember);
    }
}
