package com.encore.basic.repository;

import com.encore.basic.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository // 내부 @Component를 통해 "스프링 빈"으로 등록
public class MemoryMemberRepository implements MemberRepository{
    private final List<Member> memberDB;
    public MemoryMemberRepository(){
        memberDB = new ArrayList<>();
    }

    @Override
    public List<Member> members(){
        return memberDB;
    }

    @Override
    public void memberCreate(Member member){
        memberDB.add(member);
    }

    @Override
    public Member findById(int id) {
        for(Member member : memberDB){
            if(member.getId() == id){
                return member;
            }
        }
        return null;
    }
}
