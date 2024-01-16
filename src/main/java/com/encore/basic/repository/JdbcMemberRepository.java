package com.encore.basic.repository;

import com.encore.basic.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcMemberRepository implements MemberRepository{
    //Datasource는 DB와 JDBC에서 사용하는 DB연결 드라이버 객체
    //야믈 파일에서 설정한 정보를 담고 있음.
    @Autowired
    private DataSource dataSource;

    @Override
    public List<Member> findAll() {
        return null;
    }

    @Override
    public Member save(Member member) {
        try{
            Connection connection = dataSource.getConnection();
            String sql = "insert into member(name, email, password) values(?,?,?)";
            // preparedStatement : ? 빠진 SQL
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // ?, ?, ? 값들을 세팅
            preparedStatement.setString(1, member.getName());
            preparedStatement.setString(2, member.getEmail());
            preparedStatement.setString(3, member.getPassword());
            preparedStatement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
        return member;
    }

    @Override
    public Optional<Member> findById(int id) {
        return Optional.empty();
    }
}
