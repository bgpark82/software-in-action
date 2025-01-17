package com.bgpark.springjpaappv1.learning;

import com.bgpark.springjpaappv1.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
public class PersistenceContextTest {
    
    @Autowired
    private EntityManager em;

    @Autowired
    private EntityManagerFactory emf;

    @Test
    void 영속성_컨텍스트_1차_캐시() {
        Member member = new Member();
        member.setName("회원1");
        // 아이디가 존재하면 detach된 엔티티라고 판단하고 저장되지 않는다
        // 그럼 Merge는 가능하겠네? -> 불가, 영속성 컨텍스트에 없는 엔티티를 merge하려고 하기 때문 (아래 테스트 참조)
        // member.setId(1L);

        // 영속성 컨텍스트에 저장
        em.persist(member);

        // 1차 캐시에 저장된 엔티티 반환
        Member findMember = em.find(Member.class, 1L);
        System.out.println(findMember);
    }

    @Test
    @Disabled
    void 영속성_컨텍스트_1차_캐시_merge() {
        // detach된 객체 (로 판단)
        Member member = new Member();
        member.setName("회원1");
        member.setId(1L);

        // 영속성 컨텍스트에 merge
        // member는 실제 detach된 객체가 아니라, detach된 것이라 가정된 상태이다
        // detach되려면 영속성 컨텍스트에 엔티티가 존재했어야 한다
        // 하지만 실제로는 없는 상태
        // StaleObjectStateException 발생
        em.merge(member);

        Member findMember = em.find(Member.class, 1L);
    }

    @Test
    void 영속성_컨텍스트_1차_null() {
        Member member = new Member();
        member.setName("회원1");
        // 아이디가 존재하면 detach된 엔티티라고 판단하고 저장되지 않는다
        // 그럼 Merge는 가능하겠네? -> 불가, 영속성 컨텍스트에 없는 엔티티를 merge하려고 하기 때문 (아래 테스트 참조)
        // member.setId(1L);

        // 영속성 컨텍스트에 저장
        em.persist(member);

        // 1차 캐시에 저장된 엔티티 반환
        Member findMember1 = em.find(Member.class, 1L);
        Member findMember2 = em.find(Member.class, 2L);
        System.out.println(findMember1);
        System.out.println(findMember2);
    }

    @Test
    void 영속성_컨텍스트_1차_캐시_동일성() {
        Member member = new Member();
        member.setName("회원1");

        em.persist(member);

        // Repeatable Read의 Transaction Isolation 레벨을 1차 캐시가 제공
        // 현재 트랜잭션 내에서 여러번 데이터를 읽을 수 있다
        Member findMember1 = em.find(Member.class, 1L);
        Member findMember2 = em.find(Member.class, 1L);
        assertThat(findMember1 == findMember2).isTrue();
    }

    @Test
    void 영속성_컨텍스트_쓰기_지연() {
        Member member1 = new Member();
        member1.setName("회원1");
        Member member2 = new Member();
        member2.setName("회원2");

        EntityManager em = emf.createEntityManager();
        // @PersistenceContext로 주입된 EntityManager는 스프링이 제공하는 프록시
        // 프록시이므로 트랜잭션 범위 내에서만 EntityManager 사용가능
        // 그래서 여기서는 EntityManagerFactory로 부터 생성된 원본 엔티티 매니저를 사용해야 함
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        em.persist(member1);
        em.persist(member2);

        // 이때 flush하면서 INSERT 쿼리 전달
        // INSERT member1
        // INSERT member2
        transaction.commit();
    }

    @Test
    void 영속성_컨텍스트_dirty_checking() {
        Member member1 = new Member();
        member1.setName("회원1");

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        em.persist(member1);

        Member savedMember = em.find(Member.class, 1L);
        // 영속성 컨텍스트의 1차 캐시에 저장된 데이터와 비교
        // 다르다면 쓰기 지연 SQL 저장소에서 UPDATE 구문 생성
        // 영속성 컨텍스트에 존재하는 경우에만 가능
        savedMember.setName("회원2");

        transaction.commit();
    }

    @Test
    void 영속성_컨텍스트_삭제() {
        Member member1 = new Member();
        member1.setName("회원1");

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        em.persist(member1);

        Member savedMember = em.find(Member.class, 1L);
        // DELETE 쿼리 생성
        em.remove(savedMember);

        transaction.commit();
    }

    @Test
    void 영속성_컨텍스트_flush() {
        // flush는 commit 혹은 JPQL 쿼리 작성시 자동 실행
        // JPQL의 경우 영속성 컨텍스트를 사용하지 못하니까??
        // 영속성 컨텍스트를 비우지 않는다!!!
        // 영속성 컨텍스트의 변경 내용을 데이터베이스에 반영만 할 뿐!!
        Member member1 = new Member();
        member1.setName("회원1");

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        em.persist(member1);
        em.flush();
        System.out.println("flush 호출, 쿼리 전송 시점");
        transaction.commit();
        System.out.println("commit 호출");
    }
}
