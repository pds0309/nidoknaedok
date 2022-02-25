package com.dao.member;

import com.dto.member.MemberDTO;
import org.apache.ibatis.session.SqlSession;

public class MemberDAO {

    private static final String DIR = "com.config.MemberMapper.";

    public int signUpMember(SqlSession session, MemberDTO.SignUp member) {
        return session.insert(DIR + "signUpMember", member);
    }

    public long existCheckByName(SqlSession session, String name) {
        return session.selectOne(DIR + "existCheckById", name);
    }

    public long existCheckByEmail(SqlSession session, String email) {
        return session.selectOne(DIR + "existCheckByEmail", email);
    }
}
