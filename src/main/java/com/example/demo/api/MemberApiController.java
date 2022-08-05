package com.example.demo.api;


import com.example.demo.domain.Member;
import com.example.demo.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;
    // 이렇게 할 경우, 엔티티가 그대로 노출된다(보안위험), 각 스펙에 맞는 요구사항을 맞추기위해 엔티티를 수정하면
    // 유지보수에 어려움이 있다.(계속해서 연관된 클레스에서 수정해줘야하기 때문
//    @PostMapping("api/v1/members")
//    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member){
//
//        Long id = memberService.join(member);
//        return new CreateMemberResponse(id);
//
//    }

    @Data
    static class CreateMemberRequest{
        private String name;
    }

    @Data
    static class CreateMemberResponse{
        private Long id;

        public CreateMemberResponse(Long id){
            this.id = id;
        }
    }

    // 실무에서는 member 엔티티의 데이터가 필요한 API가 계속 증가하게 된다. 어떤 API는 name 필드가
    //필요하지만, 어떤 API는 name 필드가 필요없을 수 있다. 결론적으로 엔티티 대신에 API 스펙에 맞는 별도의 DTO를 노출시킨다.
    // DTO(CreateMemberRequest, CreateMemberResponse)를 사용하여 엔티티와 프레젠테이션 계층을 위한 로직을 분리
    // 엔티티가 변해도 API 스펙이 변하지 않는다.
    @PostMapping("api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request){

        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    // 회원수정 api
    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id,
                                               @RequestBody @Valid UpdateMemberRequest request) {
        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @Data
    static class UpdateMemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor // Builder(생성자)를 만들어주는 애노테이션
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    @GetMapping("/api/v2/members")
    public Result membersV2() {
        List<Member> findMembers = memberService.findMembers();
        //엔티티 -> DTO 변환
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());
        System.out.println(collect);
        return new Result(collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
    }

}
