package com.encore.basic.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
//Entity 어노테이션을 통해 mariaDB 테이블 및 컬럼을 자동 생성
//Class 명은 테이블명, 변수명은 컬럼명
@Entity
@NoArgsConstructor //기본 생성자 말고 다른 생성자가 있을 때 추가해줘야 함.
//// JPA가 모든 속성에 setter로 런타임에 값을 넣어줘야 하기 때문이다.
public class Member {
    @Setter
    @Id //pk 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; //Repository에서 set하기 위해 어쩔 수 없이 붙임 (DB와 싱크를 맞추기 위해 필요함)
    // String은 DB의 varchar로 자동 변환
    @Setter
    private String name;
    @Column(nullable = false, length = 50)
    private String email;
    @Setter
    private String password;
    @Setter
    @Column(name = "created_time") //name 옵션을 통해 DB의 컬럼명 별도 지정 가능
    @CreationTimestamp
    private LocalDateTime create_time;
    @UpdateTimestamp
    private LocalDateTime updated_time;
    public Member(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
        //this.create_time = LocalDateTime.now();
    }
    public void updateMember(String name, String password){
        this.name = name;
        this.password = password;
    }
}
