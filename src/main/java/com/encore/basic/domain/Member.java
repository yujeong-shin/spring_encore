package com.encore.basic.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class Member {
    @Setter
    private int id; //Repository에서 set하기 위해 어쩔 수 없이 붙임 (DB와 싱크를 맞추기 위해 필요함)
    private String name;
    private String email;
    private String password;
    @Setter
    private LocalDateTime create_time;
    public Member(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
