package com.bgpark.springjpaappv1.repository;

import com.bgpark.springjpaappv1.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberRepository {

    /**
     * Persistence -> EntityManagerFactory -> EntityManager
     * - EntityManager는 쓰레드간 공유 안됨
     * - 기본적으로 트랜잭션 시작 시점에 EntityManagerFactory를 통해 생성
     * - 여기서는 Proxy로 Transaction이 실행되면 실제 생성
     */
    @PersistenceContext
    private EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
