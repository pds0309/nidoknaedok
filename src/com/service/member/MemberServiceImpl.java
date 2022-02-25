package com.service.member;

import com.config.MySqlSessionFactory;
import com.dao.member.MemberDAO;
import com.dto.member.MemberDTO;
import com.errors.exception.NotAcceptableValueException;
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
    public void validInputName(String name) {
        SqlSession session = MySqlSessionFactory.getSession();
        try {
            if (new MemberDAO().existCheckByName(session, name).isPresent()) {
                throw new NotAcceptableValueException("이미 존재하는 이름입니다");
            }
        } finally {
            session.close();
        }
    }

    @Override
    public void validInputEmail(String email) {
        SqlSession session = MySqlSessionFactory.getSession();
        try {
            if (new MemberDAO().existCheckByEmail(session, email).isPresent()) {
                throw new NotAcceptableValueException("이미 존재하는 이메일입니다");
            }
        } finally {
            session.close();
        }
    }

}
