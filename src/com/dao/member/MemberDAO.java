package com.dao.member;

import com.dto.member.MemberDTO;
import org.apache.ibatis.session.SqlSession;

import java.util.Optional;

public class MemberDAO {

    private static final String DIR = "com.config.MemberMapper.";

    public int signUpMember(SqlSession session, MemberDTO.SignUp member) {
        return session.insert(DIR + "signUpMember", member);
    }

    public Optional<MemberDTO.Info> findByName(SqlSession session, String name) {
        return Optional.ofNullable(session.selectOne(DIR + "findByName", name));
    }

    public Optional<MemberDTO.Info> findByEmail(SqlSession session, String email) {
        return Optional.ofNullable(session.selectOne(DIR + "findByEmail", email));
    }

    public Optional<MemberDTO.Info> findById(SqlSession session, long id) {
        return Optional.ofNullable(session.selectOne(DIR + "findById", id));
    }

    public int updateMember(SqlSession session, MemberDTO.Update member) {
        return session.update(DIR + "updateMember", member);
    }

    public Optional<MemberDTO.Info> findByEmailAndPassword(SqlSession session, MemberDTO.SignIn member) {
        return Optional.ofNullable(session.selectOne(DIR + "findByEmailAndPassword", member));
    }

    public int deleteMember(SqlSession session, MemberDTO.Delete member) {
        return session.delete(DIR + "deleteMember", member);
    }
}
