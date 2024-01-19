package com.encore.basic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    //Docket은 swagger구성의 핵심 기능 클래스
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select() // 어떤 컨트롤러 또는 어떤 api를 swagger문서에 포함시킬지 선택하기 위한 메서드
                //모든 RequestHandler들을 문서화 대상으로 선택한다는 설정
                //패턴정의 : * (직계), **(자손단위)
                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any()) 모든 컨트롤러에 스웨거 적용
                .paths(PathSelectors.ant("/rest/**"))
                .build();
    }
    //스웨거에 authorize자물쇠 버튼 활성화를 위해서는 jwt, session등의 별도의 설정 필요
}