package com.encore.basic.controller;

import com.encore.basic.domain.MemberRequestDto;
import com.encore.basic.domain.MemberResponseDto;
import com.encore.basic.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController //모든 메서드에 @ResponseBody 붙여, 화면 전달이 아닌 데이터 전달만 하게 만듬 => CSR
@RequestMapping("/rest")
public class MemberRestController {
    private final MemberService memberService;
    @Autowired
    public MemberRestController(MemberService memberService){
        this.memberService = memberService;
    }

    //회원 목록 조회
    @GetMapping("members")
    public List<MemberResponseDto> members(){
        return memberService.findAll();
    }

    //회원가입
    @PostMapping("members/create")
    public String createMember(@RequestBody MemberRequestDto memberRequestDto){
        memberService.createMember(memberRequestDto);
        return "ok";
    }
    // 다음에는 MemberService의 createMember함수 리턴타입을 MemberRequestDto로 바꿔
    // 컨트롤러의 createMember함수에서 memberRequestDto을 return하게 만들어야 함.

    // Home 생성
    @GetMapping("/")
    public String home(){
        return "member/header";
    }

    @GetMapping("member/find/{id}")
    public ResponseEntity<Map<String, Object>> findMember(@PathVariable int id){
        MemberResponseDto memberResponseDto = null;
        try{
            memberResponseDto = memberService.findById(id);
            return ResponseEntityController.responseMessage(HttpStatus.OK, memberResponseDto);

        }catch(Exception e){
            e.printStackTrace();
            //orElseThrow에서 넘어온 에러 메시지 정보를 실제 사용자에게 주자
            return ResponseEntityController.errResponseMessage(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }


    @DeleteMapping("member/delete/{id}")
    public String deleteMember(@PathVariable int id){
        memberService.deleteMember(id);
        return "ok";
    }

    @PatchMapping("member/update")
    public MemberResponseDto updateMember(@RequestBody MemberRequestDto memberRequestDto){
        // 나중에는 RequestDto에 id 빼고 설계. 이럴 경우 @PathVariable로 id만 받아주면 됨
            memberService.updateMember(memberRequestDto);
            return memberService.findById(memberRequestDto.getId());
    }
}