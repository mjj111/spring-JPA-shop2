package com.example.demo.Service;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.MemberService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class) // 스프링과 테스트 통합
@SpringBootTest //  스프링 부트 띄우고 테스트(이게 없으면 @Autowired 다 실패)
@Transactional //반복 가능한 테스트 지원, 각각의 테스트를 실행할 때마다 트랜잭션을 시작하고 테스트가 끝나면,
                // 트랜잭션을 강제로 롤백 (이 어노테이션이 테스트 케이스에서 사용될 때만 롤백)
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test // junit
    public void 회원가입() throws Exception{

        //Given
        Member member = new Member();
        member.setName("명준");

        //When
        Long saveId = memberService.join(member);

        //Then // Equals를 사용할 떄 jupiter api
        Assertions.assertEquals(member,memberService.findOne(saveId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception{
        //Given
        Member member1 = new Member();
        Member member2 = new Member();
        member1.setName("명준");
        member2.setName("명준");

        //When
        memberService.join(member1);
        memberService.join(member2);
        // m1이 저장되고, 둘이 이름이 같으니 m2가 저장하려할 때 이름 중복 예외가 발생한다.

        //Then + jupiter api
        Assertions.fail("예상한대로 예외발생!");
    }

}
