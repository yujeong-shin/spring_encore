package com.encore.basic.controller;

import com.encore.basic.domain.Hello;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

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
    //@RequestMapping(value = "string", method = RequestMethod.GET) //@GetMapping("string")와 같은 의미
    @GetMapping("string")
    @ResponseBody
    public String helloString(){
        return "hello_string";
    }

    @GetMapping("json")
    @ResponseBody
    public Hello helloJson(){
        Hello hello = new Hello();
        hello.setName("kamill");
        hello.setEmail("ka@naver.com");
        hello.setPassword("1234");
        System.out.println(hello);
        return hello;
    }

    @GetMapping("screen")
    public String helloScreen(){
        return "screen";
    }

    // 1)parameter방식 : 주소/?키1=밸류1&키2=밸류2
    // ex) localhost:8080/hello/screen-model?name=hongildong
    @GetMapping("screen-model-param")
    //@RequestParam(파라미터 호출방식) ==> localhost:8080/member-model-param?name=hongildong 방식으로 호출
    public String helloScreenModelParam(@RequestParam(value = "name")String inputName, Model model){
        //화면에 data를 넘기고 싶을 때는 model 객체 사용
        //model에 key:value 형식으로 전달
        model.addAttribute("myData", inputName);
        return "screen";
    }

    // 2)pathVariable방식 : 주소/값
    // localhost:8080/hello/screen-model/hongildong
    // -> 서버의 특정 자원의 값을 가져올 때 사용. 요즘은 2번 방식이 더 많다.
    //pathVariable방식은 url을 통해 자원의 구조를 명확하게 표현할 수 있어, 좀 더 RestFul API 디자인에 적합하다.
    @GetMapping("screen-model-path/{id}")
    //localhost:8080/member-model-path/1 방식으로 호출
    public String helloScreenModelPath(@PathVariable int id, Model model){
        model.addAttribute("myData", id);
        return "screen";
    }

    //form 태그로 x-www-urlencoded 데이터 처리
    @GetMapping("form-screen") //사용자가 이 url로 들어오면
    public String formScreen(){
        return "hello-form-screen"; //값 입력을 위한 html전달
    }
    @PostMapping("form-post-handle1") //데이터가 넘어오겠구나 = 매개변수가 있겠구나 = 받아야지
    //form 태그를 통한 body의 데이터 형태가 key1=value1, key2=value2
    //GET요청의 파라미터 호출방식과 동일하기 때문에 @RequestParam 사용⭐
    @ResponseBody
    public String formPostHandle1(@RequestParam(value = "name")String name,
                                 @RequestParam(value = "email")String email,
                                 @RequestParam(value = "password")String password){
        System.out.println("name = " + name);
        System.out.println("email = " + email);
        System.out.println("password = " + password);
        return "정상처리";
    }

    @PostMapping("form-post-handle2")
    @ResponseBody
    //Spring에서 Hello클래스의 인스턴스를 자동 매핑하여 생성
    //form-data 형식. x-www-form-urlencoded 형식의 경우만 사용 가능
    //이를 데이터 바인딩이라 부른다. (Hello 클래스에 setter 필수⭐)
    public String formPostHandle2(Hello hello){
        System.out.println(hello);
        return "정상처리";
    }

    //json데이터 처리
    @GetMapping("json-screen")
    public String jsonScreen(){
        return "hello-json-screen";
    }

    @PostMapping("/json-post-handle1")
    @ResponseBody
    //@RequestBody는 json으로 post 요청이 들어왔을 때 body에서 데이터를 꺼내기 위해 사용
    public String jsonPostHandle1(@RequestBody Map<String, String> body){
        System.out.println("이름 : " + body.get("name"));
        System.out.println("email :" + body.get("email"));
        System.out.println("password : " + body.get("password"));
        Hello hello = new Hello();
        hello.setName(body.get("name"));
        hello.setEmail(body.get("email"));
        hello.setPassword(body.get("password"));
        //return "정상처리"; //hello-json-screen안 js코드에 response에 담기면서 제대로 출력이 안됨.
        //이 메서드에서는 대충 ok만 남기고, js코드에서 화면 전환을 해줘야 함.⭐
        return "ok";
    }

    @PostMapping("/json-post-handle2")
    @ResponseBody
    public String jsonPostHandle2(@RequestBody JsonNode body){
        Hello hello = new Hello();
        hello.setName(body.get("name").asText());
        hello.setEmail(body.get("email").asText());
        hello.setPassword(body.get("password").asText());
        return "ok";
    }

    @PostMapping("/json-post-handle3")
    @ResponseBody
    public String jsonPostHandle3(@RequestBody Hello hello){
        System.out.println(hello);
        return "ok";
    }
}
