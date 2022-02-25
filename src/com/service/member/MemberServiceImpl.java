package com.service.member;

import com.config.MySqlSessionFactory;
import com.dao.member.MemberDAO;
import com.dto.member.MemberDTO;
import com.errors.exception.InvalidValueException;
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
    public MemberDTO.Info existCheckByName(String name) {
        SqlSession session = MySqlSessionFactory.getSession();
        try {
            return new MemberDAO().existCheckByName(session, name)
                    .orElseThrow(() -> new InvalidValueException("이미 존재하는 이름입니다"));
        } finally {
            session.close();
        }
    }

    @Override
    public MemberDTO.Info existCheckByEmail(String email) {
        SqlSession session = MySqlSessionFactory.getSession();
        try {
            return new MemberDAO().existCheckByEmail(session, email)
                    .orElseThrow(() -> new InvalidValueException("이미 존재하는 이메일 입니다"));
        } finally {
            session.close();
        }
    }
}
