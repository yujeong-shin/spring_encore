package com.encore.basic.repository;

import com.encore.basic.domain.Member;

import java.util.List;

public interface MemberRepository {
    public List<Member> members();
    public void memberCreate(Member member);
    public Member findById(int id);
}
