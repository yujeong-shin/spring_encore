package com.encore.basic.controller;

import com.encore.basic.domain.Hello;
import com.encore.basic.domain.Member;
import com.encore.basic.domain.MemberRequestDto;
import com.encore.basic.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("members")
public class MemberController {
    private final MemberService memberService;
    MemberController(){
        memberService = new MemberService();
    }

    //회원 목록 조회
    @GetMapping()
    public String members(Model model){
        // memberResponseDtos 가져옴
        // 사용자는 Member 객체가 아닌 DTO 객체에 있는 정보만 필요함
        model.addAttribute("members", memberService.members());
        return "member/memberList";
    }

    //회원가입
    @GetMapping("create")
    public String createMember(){
        return "member/member-create-screen";
    }

    @PostMapping("create")
    @ResponseBody
    public String create(MemberRequestDto memberRequestDto){
        System.out.println(memberRequestDto);
        memberService.memberCreate(memberRequestDto);
        return "ok";
    }
}