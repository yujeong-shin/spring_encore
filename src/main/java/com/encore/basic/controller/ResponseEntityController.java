package com.encore.basic.controller;

import com.encore.basic.domain.Member;
import com.encore.basic.domain.MemberResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("response/entity")
public class ResponseEntityController {
    //@ResponseStatus 어노테이션 방식
    @GetMapping("responsestatus")
    @ResponseStatus(HttpStatus.CREATED)//간단하게 header에 원하는 상태코드 세팅
    public String responseStatus(){
        return "OK";
    }

    @GetMapping("responsestatus2")
    @ResponseStatus(HttpStatus.CREATED) //header에는 상태코드 세팅 - 201
    public Member responseStatus2(){
        Member member = new Member("choi", "choi@naver.com", "1234");
        return member; //body에는 Member 객체 데이터
    }

    //ResponseEntity 객체를 직접 생성한 방식
    @GetMapping("custom1")
    public ResponseEntity<Member> custom1(){
        Member member = new Member("choi", "choi@naver.com", "1234");
        return new ResponseEntity<>(member, HttpStatus.CREATED);
        //header에는 상태코드, body에는 json
    }

    //ResponseEntity<String>일 경우 text/html로 설정
    //활용도 없다.
    @GetMapping("custom2")
    public ResponseEntity<String> custom2(){
        String html = "<h1>없는 ID입니다.</h1>";
        return new ResponseEntity<>(html, HttpStatus.NOT_FOUND);
        //header에는 상태코드, body에는 html
    }

    //new ResponseEntity 방식으로 많이 씀⭐
    //map 형태의 메시지 커스텀
    // {
    //   "status" : "ok"
    //   "error message" : "error 발생"
    // }

    public static ResponseEntity<Map<String, Object>> errResponseMessage(HttpStatus status, String message){
        Map<String, Object> body = new HashMap<>();
        body.put("status", Integer.toString(status.value())); //responseBody에 출력되도록 넣기
        body.put("error message", message);
        return new ResponseEntity<>(body, status); //status에 담긴 값은 header로 나가고, body에 담긴 Map은 json 형태로 나감.
    }

    //status 201(생성), message : 객체
    // {
    //   "status" : "ok"
    //   "error message" : {aa, bb, cc}
    // }
    // 한 필드에 여러 개의 값이 들어오는 경우 -> Object 사용
    // 실무에서는 필요한 값들을 모두 body에 넣어서 전달
    public static ResponseEntity<Map<String, Object>> responseMessage(HttpStatus status, Object object){
        Map<String, Object> body = new HashMap<>();
        body.put("status", Integer.toString(status.value()));
        body.put("message", object);
        return new ResponseEntity<>(body, status); //status에 담긴 값은 header로 나가고, body에 담긴 Map은 json 형태로 나감.
    }

    //메서드 체이닝 : ResponseEntity의 클래스메서드 사용
    @GetMapping("chaining1")
    public ResponseEntity<Member> chaining1(){
        Member member = new Member("choi", "choi@naver.com", "1234");
        return ResponseEntity.ok(member);
    }

    @GetMapping("chaining2")
    public ResponseEntity<Member> chaining2(){
        return ResponseEntity.notFound().build(); //body 없음
    }

    //다양한 상태값을 쓰고 싶다면 이렇게 사용하자 !!
    @GetMapping("chaining3")
    public ResponseEntity<Member> chaining3(){
        Member member = new Member("choi", "choi@naver.com", "1234");
        return ResponseEntity.status(HttpStatus.CREATED).body(member);
    }
}
