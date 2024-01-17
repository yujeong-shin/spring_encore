package com.encore.basic.repository;

import com.encore.basic.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaMemberRepository implements MemberRepository{
    //EntityManager는 JPA의 핵심클래스(객체)
    //Entity의 생명주기를 관리. 데이터베이스와의 모든 상호작용을 책임.
    //엔티티를 대상으로 CRUD하는 기능을 제공
    private final EntityManager entityManager;
    public JpaMemberRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Member save(Member member) {
        //persist : 전달된 엔티티(member)가 EntityManager의 관리 상태가 되도록 만들어주고,
        // 트랜잭션이 커밋될 때 데이터베이스에 저장. insert
        // EntityManager는 매개변수로 넘어온 member의 id값이 이미 존재하면 update, 아니면 insert
        entityManager.persist(member);
        return member;
    }
    @Override
    public Optional<Member> findById(int id) {
        //find메서드는 pk를 매개변수로 준다.
        Member member = entityManager.find(Member.class, id); //Member 테이블 가서 id 값 가지는 member 찾음
        //그 외 컬럼으로 조회할 때는 select m from Member m where m.name = :name => 아래 findByName() 메서드
        return Optional.ofNullable(member);
    }

    @Override
    public void delete(Member member) {
        //remove 메서드 사용
        //update의 경우 merge 메서드 사용.
    }

    //pk아닌 컬럼으로 조회
    public List<Member> findByName(String name){
        List<Member> members = entityManager.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name).getResultList();
        return members;
    }

    @Override
    //따로 제공하는 문법이 없어 직접 만들어야 함.
    public List<Member> findAll() {
        //jpql : jpa의 쿼리문법
        //장점 : DB에 따라 문법이 달라지지 않는 객체지향 언어, 컴파일 타임에서 check⭐!!
        //단점 : DB 고유의 기능과 성능을 극대화하기는 어려움.
        List<Member> members = entityManager.createQuery("select m from Member m", Member.class).getResultList();
        return members;
    }
}