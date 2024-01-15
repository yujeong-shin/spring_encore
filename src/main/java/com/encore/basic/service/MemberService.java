package com.encore.basic.service;

import com.encore.basic.domain.Member;
import com.encore.basic.domain.MemberRequestDto;
import com.encore.basic.domain.MemberResponseDto;
import com.encore.basic.repository.MemberRepository;
import com.encore.basic.repository.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service // 싱글톤 컴포넌트로 생성 -> 내부 @Component를 통해 "스프링 빈"으로 등록
// 스프링 빈이랑, 스프링이 생성하고 관리하는 객체를 의미
// 제어의 역전(Inversion of Control)
// IoC 컨테이너가 스프링 빈을 관리한다.(빈 생성, 의존성 주입)
public class MemberService {
    private final MemoryMemberRepository memoryMemberRepository;
    @Autowired
    public MemberService(MemoryMemberRepository memoryMemberRepository) {
        this.memoryMemberRepository = memoryMemberRepository;
    }

    static int total_id;
    public List<MemberResponseDto> members(){
        List<Member> members = memoryMemberRepository.members();
        List<MemberResponseDto> memberResponseDtos = new ArrayList<>();
        for(Member member : members){
            MemberResponseDto memberResponseDto = new MemberResponseDto(member.getId(), member.getName(), member.getEmail(), member.getPassword());
            memberResponseDtos.add(memberResponseDto);
        }
        return memberResponseDtos;
    }

    // 사용자의 입력 값이 담긴 DTO를 통해, 실제 시스템에서 사용되는 정보를 조합해 Member 객체로 변환 후 저장
    public void memberCreate(MemberRequestDto memberRequestDto){
        LocalDateTime now = LocalDateTime.now();
        ++total_id;
        Member member = new Member(total_id, memberRequestDto.getName(), memberRequestDto.getEmail(), memberRequestDto.getPassword(), now);
        memoryMemberRepository.memberCreate(member);
    }

    public MemberResponseDto findById(int id){
        //Member 객체를 MemberRequestDto로 변환
        Member member = memoryMemberRepository.findById(id);
        MemberResponseDto memberResponseDto = new MemberResponseDto(member.getId(), member.getName(), member.getEmail(), member.getPassword());
        return memberResponseDto;
    }

}
