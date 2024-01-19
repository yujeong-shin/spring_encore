package com.encore.basic.servletjsp;

import com.encore.basic.domain.Hello;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/hello-servlet-rest-get")
public class HelloServletRestGet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Hello hello = new Hello();
        hello.setName("shin");
        hello.setEmail("shin@naver.cm");
        hello.setPassword("1234");

        // 직렬화 : 자바 객체 -> json
        // 객체를 데이터 스트림으로 만들어, 스트림에 객체를 출력
        ObjectMapper objectMapper = new ObjectMapper();
        String json_hello = objectMapper.writeValueAsString(hello);

        // 응답 header
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        // 응답 body
        PrintWriter out = resp.getWriter();
        out.print(json_hello);

        //버퍼를 통해 조립이 이루어지므로, 버퍼를 비우는 과정.
        out.flush();
    }
}
