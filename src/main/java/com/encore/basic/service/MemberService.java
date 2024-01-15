package com.encore.basic.service;

import com.encore.basic.domain.Member;
import com.encore.basic.domain.MemberRequestDto;
import com.encore.basic.domain.MemberResponseDto;
import com.encore.basic.repository.MemoryMemberRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MemberService {
    private final MemoryMemberRepository memoryMemberRepository;
    static int total_id;
    public MemberService(){
        memoryMemberRepository = new MemoryMemberRepository();
    }
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
}
