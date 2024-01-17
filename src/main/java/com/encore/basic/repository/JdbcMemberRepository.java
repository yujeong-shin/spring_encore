package com.encore.basic.repository;

import com.encore.basic.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
        List<Member> members = new ArrayList<>();
        try{
            Connection connection = dataSource.getConnection();
            String sql = "select * from member";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                LocalDateTime now = resultSet.getTimestamp("create_time").toLocalDateTime();

                Member member = new Member(name, email, password);
                member.setId(id);
                member.setCreate_time(now);

                members.add(member);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return members;
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
    public Optional<Member> findById(int inputId) {
        Member member = null;
        try{
            Connection connection = dataSource.getConnection();
            String sql = "select * from member where id = ?";
            // preparedStatement : ? 빠진 SQL
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // ?값을 세팅
            preparedStatement.setInt(1, inputId);
            // 결과 가져오기
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");
            LocalDateTime now = resultSet.getTimestamp("create_time").toLocalDateTime();

            member = new Member(name, email, password);
            member.setId(id);
            member.setCreate_time(now);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return Optional.ofNullable(member);
    }

    @Override
    public void delete(Member member) {

    }
}
