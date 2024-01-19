package com.encore.basic.servletjsp;

import com.encore.basic.domain.Hello;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/hello-servlet-rest-post")
public class HelloServletRestpPost extends HttpServlet{
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        BufferedReader br = req.getReader();
//        StringBuilder sb = new StringBuilder();
//
//        // sb에 body 데이터 전부 가져오기
//        String line = null;
//        while((line = br.readLine()) != null) {
//            sb.append(line);
//        }

        // 역직렬화 : json -> 자바 객체
        // 스트림에서 String을 읽어 와 객체로 만듬
        if(req.getContentType() == "application/json"){
            ObjectMapper mapper = new ObjectMapper();
            //Hello hello = mapper.readValue(sb.toString(), Hello.class);
            Hello hello = mapper.readValue(req.getReader(), Hello.class);
            System.out.println(hello);
        }

        // 응답 header
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");

        // 응답 body
        PrintWriter out = resp.getWriter();
        out.print("ok");
        out.flush();
    }
}
