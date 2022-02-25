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
            session.commit();
        } finally {
            session.close();
        }
        return status;
    }

    @Override
    public long existCheckByName(String name) {
        SqlSession session = MySqlSessionFactory.getSession();
        long result = 0;
        try {
            result = new MemberDAO().existCheckByName(session, name);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public long existCheckByEmail(String email) {
        SqlSession session = MySqlSessionFactory.getSession();
        long result = 0;
        try {
            result = new MemberDAO().existCheckByEmail(session, email);
        } finally {
            session.close();
        }
        return result;
    }
}
