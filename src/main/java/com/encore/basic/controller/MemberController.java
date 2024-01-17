package com.encore.basic.controller;

import com.encore.basic.domain.Hello;
import com.encore.basic.domain.Member;
import com.encore.basic.domain.MemberRequestDto;
import com.encore.basic.domain.MemberResponseDto;
import com.encore.basic.service.MemberService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;

@Controller // 내부 @Component를 통해 "스프링 빈"으로 등록
//@RequiredArgsConstructor
public class MemberController {
//    //@Autowired //의존성 주입(DI) 방법1 => 필드 주입방식.
//    //private MemberService memberService;

    //의존성 주입방법2 => 생성자 주입방식. 가장 많이 사용하는 방법 ⭐⭐
    //장점 : final 키워드를 통해 상수로 사용함으로써 재생성을 막는다.⭐
    // 다형성 구현 가능 == 역할과 구현 분리⭐⭐, 순환 참조 방지
    // 생성자가 1개밖에 없을 때에는 Autowired 생략 가능.
    private final MemberService memberService;
    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

//    //의존성 주입방법3 => @RequiredArgsConstructor를 이용한 방식
//    //@RequiredArgsConstructor
//    //@NonNull 어노테이션이 붙어 있는 필드 또는 초기화되지 않은 final 필드를 대상으로 생성자 생성.
//    private final MemberService memberService;

    //회원 목록 조회
    @GetMapping("members")
    public String members(Model model){
        // memberResponseDtos 가져옴
        // 사용자는 Member 객체가 아닌 DTO 객체에 있는 정보만 필요함
        model.addAttribute("members", memberService.findAll());
        return "member/memberList";
    }

    //회원가입
    @GetMapping("members/create")
    public String createMember(){
        return "member/member-create-screen";
    }

    @PostMapping("members/create")
    public String create(MemberRequestDto memberRequestDto){
//        //트랜잭션 및 예외처리 테스트
//        // Service에서 예외가 발생하면 Service는 @Transactional 어노테이션을 통해 DB rollback ⭐
//        // Controller는 사용자에게 적절한 화면 return ⭐
//        try{
//            System.out.println(memberRequestDto);
//            memberService.createMember(memberRequestDto);
//            return "redirect:/members"; //url 리다이렉트
//        }catch (IllegalArgumentException e){
//            return "404-error-page"; //404 에러 페이지
//        }
        memberService.createMember(memberRequestDto);
        return "redirect:/members"; //url 리다이렉트
    }

    // Home 생성
    @GetMapping("/")
    public String home(){
        return "member/header";
    }

    @GetMapping("member/find")
    public String findMember(@RequestParam("id") int id, Model model){
        try {
            // Unckecked Exception이기 때문에 예외 처리가 강제되지 않아 없어도 오류가 발생하지 않는다.
            MemberResponseDto memberResponseDto = memberService.findById(id);
            model.addAttribute("member", memberResponseDto);
            return "member/member-find-screen";
        } catch (EntityNotFoundException e) {
            return "404-error-page";
        }
    }

    @GetMapping("member/delete")
    public String deleteMember(@RequestParam("id") int id){
        try{
            memberService.deleteMember(id);
            return "redirect:/members";
        } catch (EntityNotFoundException e){
            return "404-error-page";
        }
    }

    @PostMapping("member/update")
    public String updateMember(MemberRequestDto memberRequestDto, Model model){
        try{
            memberService.updateMember(memberRequestDto);

            model.addAttribute("member", memberRequestDto);
            return "redirect:/member/find?id=" + memberRequestDto.getId();
        }
        catch (EntityNotFoundException e){
            return "404-error-page";
        }
    }
}