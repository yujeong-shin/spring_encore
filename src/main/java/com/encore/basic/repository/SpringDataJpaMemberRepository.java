package com.encore.basic.repository;

import com.encore.basic.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//Spring Data Jpa의 기본 기능을 쓰기 위해서는 JpaRepository를 상속해야함
//상속 시 Entity명과 해당 Entitiy의 PK 타입을 명시
//<Class명, PK타입>
//구현 클래스와 스펙은 SimpleJpaRepository 클래스에 있고, 실질적인 구동 상황에서 hibernate 구현체에 동작을 위임⭐
public interface SpringDataJpaMemberRepository extends MemberRepository, JpaRepository<Member, Integer> { //인터페이스 간의 관계는 extends.
}
