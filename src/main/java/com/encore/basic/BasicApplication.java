package com.encore.basic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

//주로 Web서블릿 기반의 구성요소를 스캔하고, 자동으로 등록하려면 아래 어노테이션 지정
//@WebServlet, @WebFilter, @WebListener 등의 어노테이션 스캔
@ServletComponentScan
@SpringBootApplication
public class BasicApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicApplication.class, args);
	}

}
