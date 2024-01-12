package com.encore.basic.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//@Getter
//@Setter
@Data //getter, setter 및 toString, equals등 사전 구현
public class Hello {
    private String name;
    private String email;
    private String password;

}
