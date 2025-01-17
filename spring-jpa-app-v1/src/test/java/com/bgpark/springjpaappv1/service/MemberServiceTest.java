package com.bgpark.springjpaappv1.service;

import com.bgpark.springjpaappv1.domain.Member;
import com.bgpark.springjpaappv1.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 회원가입() {
        Member member = Member.builder()
                .name("park")
                .build();

        Long savedMemberId = memberService.join(member);

        assertThat(member).isEqualTo(memberRepository.findOne(savedMemberId));
    }
}