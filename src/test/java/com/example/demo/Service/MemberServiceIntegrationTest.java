package com.example.demo.Service;

import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {
//    @Autowired
//    MemberService memberService;
//    @Autowired
//    SpringDataJpaMemberRepository springDataJpaMemberRepository;
//    @Test
//    public void 회원가입() throws Exception {
//        //Given
//        Member member = new Member();
//        member.setName("hello");
//        //When
//        Long saveId = memberService.join(member);
//        //Then
//        Member findMember = springDataJpaMemberRepository.findById(saveId).get();
//        Assertions.assertEquals(member.getName(), findMember.getName());
//    }
//    @Test
//    public void 중복_회원_예외() throws Exception {
//        //Given
//        Member member1 = new Member();
//        member1.setName("spring");
//        Member member2 = new Member();
//        member2.setName("spring");
//        //When
//        memberService.join(member1);
//        IllegalStateException e = Assertions.assertThrows(IllegalStateException.class, () -> memberService.join(member2));//예외가 발생해야 한다.
//        org.assertj.core.api.Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//    }
}

