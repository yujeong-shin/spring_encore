package com.encore.basic.servletjsp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/hello-servlet-jsp-post")
public class HelloServletJspPost extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        // console에 출력
        System.out.println(name);
        System.out.println(email);
        System.out.println(password);

        // 응답 header
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");

        // 응답 body
        // Controller에서 @ResponseBody + String + "ok" 출력한 것과 동일하게 만드는 것
        PrintWriter out = resp.getWriter();
        out.print("ok");

        //버퍼를 통해 조립이 이루어지므로, 버퍼를 비우는 과정.
        out.flush();
    }
}
