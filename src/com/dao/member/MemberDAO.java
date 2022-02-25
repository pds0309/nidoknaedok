package com.dao.member;

import com.dto.member.MemberDTO;
import org.apache.ibatis.session.SqlSession;

import java.util.Optional;

public class MemberDAO {

    private static final String DIR = "com.config.MemberMapper.";

    public int signUpMember(SqlSession session, MemberDTO.SignUp member) {
        return session.insert(DIR + "signUpMember", member);
    }

    public Optional<MemberDTO.Info> existCheckByName(SqlSession session, String name) {
        return Optional.ofNullable(session.selectOne(DIR + "existCheckByName", name));
    }

    public Optional<MemberDTO.Info> existCheckByEmail(SqlSession session, String email) {
        return Optional.ofNullable(session.selectOne(DIR + "existCheckByEmail", email));
    }
}
