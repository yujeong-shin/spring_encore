package com.encore.basic.service;

import com.encore.basic.domain.Member;
import com.encore.basic.domain.MemberRequestDto;
import com.encore.basic.domain.MemberResponseDto;
import com.encore.basic.repository.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.proxy.EntityNotFoundDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
// 싱글톤 컴포넌트로 생성 -> 내부 @Component를 통해 "스프링 빈"으로 등록
// 스프링 빈이랑, 스프링이 생성하고 관리하는 객체를 의미
// 제어의 역전(Inversion of Control)
// IoC 컨테이너가 스프링 빈을 관리한다.(빈 생성, 의존성 주입)
public class MemberService {
    private final MemberRepository memberRepository;
    @Autowired
    public MemberService(SpringDataJpaMemberRepository springDataJpaMemberRepository) {
        this.memberRepository = springDataJpaMemberRepository;
    }
    public List<MemberResponseDto> findAll(){
        List<Member> members = memberRepository.findAll();
        List<MemberResponseDto> memberResponseDtos = new ArrayList<>();
        for(Member member : members){
            MemberResponseDto memberResponseDto = new MemberResponseDto();
            memberResponseDto.setId(member.getId());
            memberResponseDto.setName(member.getName());
            memberResponseDto.setEmail(member.getEmail());
            memberResponseDto.setPassword(member.getPassword());
            memberResponseDtos.add(memberResponseDto);
        }
        return memberResponseDtos;
    }

    //Transactional 어노테이션을 클래스 단위로 붙이면 모든 메서드에 각각 Transactional 적용
    //Transactional을 적용하면 한 메서드 단위로 트랜잭션 지정
    @Transactional //⭐⭐
    // 사용자의 입력 값이 담긴 DTO를 통해, 실제 시스템에서 사용되는 정보를 조합해 Member 객체로 변환 후 저장
    public void createMember(MemberRequestDto memberRequestDto) throws IllegalArgumentException {
        Member member = new Member(memberRequestDto.getName(), memberRequestDto.getEmail(), memberRequestDto.getPassword());
        memberRepository.save(member);
//        //Transaction 테스트
//        Member member = new Member(memberRequestDto.getName(), memberRequestDto.getEmail(), memberRequestDto.getPassword());
//        memberRepository.save(member);
//        if(member.getName().equals("kim")){
//            throw new IllegalArgumentException();
//        }
    }

    // Optional, 예외처리 디테일 챙기기
    //보통 서비스에서는 예외를 컨트롤러로 던져 컨트롤러에서 처리한다.
    public MemberResponseDto findById(int id) throws EntityNotFoundException {
        //Member 객체를 MemberResponseDto로 변환
        //생성자 초기화보다는 유연성이 좋다.
        // Member member = memberRepository.findById(id).orElseThrow(()-> new NoSuchElementException());
        // Optional로 객체가 반환되지 않으면(비어있으면) 예외 터져서 아래 코드로 내려가지 않음.
        //개발자 간 NoSuchElementException이 발생하면 어떻게 처리할지 다루려고 적는 것이지,
        //에러만 적는다고 페이지 상으로 404 에러가 나가지는 않음! ResponseEntity로 잘 처리하자!
        Member member = memberRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        MemberResponseDto memberResponseDto = new MemberResponseDto();
        memberResponseDto.setId(member.getId());
        memberResponseDto.setName(member.getName());
        memberResponseDto.setEmail(member.getEmail());
        memberResponseDto.setPassword(member.getPassword());
        memberResponseDto.setCreate_time(member.getCreate_time());
        return memberResponseDto;
    }

    public void deleteMember(int id) throws EntityNotFoundException {
        Member member = memberRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        memberRepository.delete(member);
    }

    public void updateMember(MemberRequestDto memberRequestDto) throws EntityNotFoundException {
        Member member = memberRepository.findById(memberRequestDto.getId()).orElseThrow(EntityNotFoundException::new);
        member.setName(memberRequestDto.getName());
        member.setPassword(memberRequestDto.getPassword());
        //member.updateMember(memberRequestDto.getName(), memberRequestDto.getPassword());
        memberRepository.save(member);
    }
}