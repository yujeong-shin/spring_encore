package com.encore.basic.repository;

import com.encore.basic.domain.Member;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MemoryMemberRepository implements MemberRepository{
    static int total_id;
    private final List<Member> memberDB;
    public MemoryMemberRepository(){
        memberDB = new ArrayList<>();
    }

    @Override
    public List<Member> findAll(){
        return memberDB;
    }

    @Override
    public Member save(Member member){
        ++total_id;
        LocalDateTime now = LocalDateTime.now();
        member.setId(total_id);
        member.setCreate_time(now);
        memberDB.add(member);
        return member;
    }

    @Override
    public Optional<Member> findById(int id) {
        for(Member member : memberDB){
            if(member.getId() == id){
                return Optional.of(member);
            }
        }
        return Optional.empty();
    }

    @Override
    public void delete(Member member) {

    }
}
