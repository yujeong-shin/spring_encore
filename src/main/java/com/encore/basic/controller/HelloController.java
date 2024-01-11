package com.encore.basic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller //http 통신을 쉽게 하게 해주는 어노테이션
//클래스 차원에서 url 경로를 지정하고 싶다면 @RequestMapping을 클래스 위에 선언하면서 경로 지정.
@RequestMapping("hello")
//@RestController //모든 요청에 ResponseBody를 붙이고 싶다면, RestController 사용
//data만 줘야할 경우, 내부 함수에 모두 @ResponseBody가 붙는다
//이 경우 String으로 화면 return이 불가하고, 데이터만 반환할 수 있다.
public class HelloController {
    //ReponseBody가 없고, return타입이 String이면 templates밑에 html파일 리턴
    //data만을 return할 때는 @ResponseBody를 붙인다.
    //@ResponseBody가 붙었을 때 반환 타입이 객체라면, JSON 형식으로 반환한다.
    @GetMapping("string")
    @ResponseBody
    public String helloString(){
        return "hello_string";
    }

    @GetMapping("json")
    @ResponseBody
    public String helloJson(){
        return "hello_string";
    }

    @GetMapping("screen")
    public String helloScreen(){
        return "screen";
    }

    //1)parameter방식 : ?키1=밸류1&키2=밸류2
    //2)pathVariable방식 : localhost:8080/hello/screen-model/hongildong  ex) localhost:8080/member/hongildong
    // -> 서버의 특정 자원의 값을 가져올 때 사용. 요즘은 2번 방식이 더 많다.
    @GetMapping("screen-model-param")
    //@RequestParam(파라미터 호출방식) ==> localhost:8080/member-model-param?name=hongildong 방식으로 호출
    public String helloScreenModelParam(@RequestParam(value = "name")String inputName, Model model){
        //화면에 data를 넘기고 싶을 때는 model 객체 사용
        //model에 key:value 형식으로 전달
        model.addAttribute("myData", inputName);
        return "screen";
    }

    //pathVariable방식은 url을 통해 자원의 구조를 명확하게 표현할 수 있어,
    // 좀 더 RestFul API 디자인에 적합하다.
    @GetMapping("screen-model-path/{id}")
    //localhost:8080/member-model-path/1 방식으로 호출
    public String helloScreenModelPath(@PathVariable int id, Model model){
        model.addAttribute("myData", id);
        return "screen";
    }
}
