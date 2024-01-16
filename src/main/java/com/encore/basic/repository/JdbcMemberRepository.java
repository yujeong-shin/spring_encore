package com.encore.basic.repository;

import com.encore.basic.domain.Member;

import java.util.List;
import java.util.Optional;

public class JdbcMemberRepository implements MemberRepository{
    @Override
    public List<Member> findAll() {
        return null;
    }

    @Override
    public Member save(Member member) {
        return member;
    }

    @Override
    public Optional<Member> findById(int id) {
        return Optional.empty();
    }
}
