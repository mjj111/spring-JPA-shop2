package com.example.demo.service;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //읽기 전용으로 최적화
//영속성 컨텍스트를 플러시 하지 않으므로 약간의 성능 향상(읽기 전용에는 다 적용)
//데이터베이스 드라이버가 지원하면 DB에서 성능 향상
@RequiredArgsConstructor
public class MemberService {

    // 필드주입 ,생성자 Injection 많이 사용, 생성자가 하나면 생략 가능
    // Autowired
    //MemberRepository memberRepository;

    //생성자 주입, 권장하며 변경 불가능한 안전한 객체 생성 가능
    //final 키워드를 추가하면 컴파일 시점에 memberRepository 를 설정하지 않는 오류를 체크할 수 있다.
    // private final MemberRepository memberRepository;
    // public MemberService(MemberRepository memberRepository) {
    //this.memberRepository = memberRepository;
    //}

    // lombok(RequiredArgsConstructor)을 통해 private final이면 알아서 생성자 주입해준다.
    private final MemberRepository memberRepository;


    // 회원가입 join + 중복검사 validateDuplicateMember
    @Transactional // 읽기가 아닌 변경이 발생할 섹션
    public Long join(Member member){
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member){
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 전체회원 조회 findMembers
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    // 단일 회원 번호로 조회 findOne
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

}
