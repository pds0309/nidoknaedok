package com.dao.member;

import com.dto.member.MemberDTO;
import org.apache.ibatis.session.SqlSession;

public class MemberDAO {

    private static final String DIR = "com.config.MemberMapper.";

    public int signUpMember(SqlSession session, MemberDTO.SignUp member) {
        return session.insert(DIR + "signUpMember", member);
    }
}
