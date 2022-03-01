package com.service.member;

import com.config.MySqlSessionFactory;
import com.dao.member.MemberDAO;
import com.dto.member.MemberDTO;
import com.errors.exception.InvalidValueException;
import com.errors.exception.NotAcceptableValueException;
import org.apache.ibatis.session.SqlSession;

import java.util.Optional;

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
    public Optional<MemberDTO.Info> findByEmail(String email) {
        SqlSession session = MySqlSessionFactory.getSession();
        try {
            return new MemberDAO().findByEmail(session, email);
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<MemberDTO.Info> findByName(String name) {
        SqlSession session = MySqlSessionFactory.getSession();
        try {
            return new MemberDAO().findByName(session, name);
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<MemberDTO.Info> findById(long id) {
        SqlSession session = MySqlSessionFactory.getSession();
        try {
            return new MemberDAO().findById(session, id);
        } finally {
            session.close();
        }
    }

    @Override
    public void validInputName(String name) {
        if (findByName(name).isPresent()) {
            throw new NotAcceptableValueException("이미 존재하는 이메일입니다");
        }
    }

    @Override
    public void validInputEmail(String email) {
        if (findByEmail(email).isPresent()) {
            throw new NotAcceptableValueException("이미 존재하는 이메일입니다");
        }
    }

    @Override
    public int updateMember(MemberDTO.Update member) {
        if (member.getId() == 0) {
            throw new InvalidValueException("입력 정보가 올바르지 않습니다");
        }
        SqlSession session = MySqlSessionFactory.getSession();
        int status = 0;
        try {
            status = new MemberDAO().updateMember(session, member);
            session.commit();
        } finally {
            session.close();
        }
        return status;
    }
}
