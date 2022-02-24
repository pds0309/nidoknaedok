package com.service.member;

import com.config.MySqlSessionFactory;
import com.dao.member.MemberDAO;
import com.dto.member.MemberDTO;
import org.apache.ibatis.session.SqlSession;

public class MemberServiceImpl implements MemberService {

    @Override
    public int signUpMember(MemberDTO.SignUp member) {
        SqlSession session = MySqlSessionFactory.getSession();
        int status = 0;
        try {
            status = new MemberDAO().signUpMember(session, member);
        } finally {
            session.close();
        }
        return status;
    }
}
