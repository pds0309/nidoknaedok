package com.service.member;

import com.config.MySqlSessionFactory;
import com.dao.member.MemberDAO;
import com.dto.member.MemberDTO;
import com.dto.member.MemberRole;
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
    public Optional<MemberDTO.Info> login(MemberDTO.SignIn member) {
        SqlSession session = MySqlSessionFactory.getSession();
        try {
            return new MemberDAO().findByEmailAndPassword(session, member);
        } finally {
            session.close();
        }
    }

    @Override
    public void validInputName(String name) {
        if (findByName(name).isPresent()) {
            throw new NotAcceptableValueException("이미 존재하는 이름입니다");
        }
    }

    /**
     * @param email: 사용자 입력 이메일
     * @return: true=정상, false=탈퇴한 놈, throw=가입불가
     */
    @Override
    public void validInputEmail(String email) {
        Optional<MemberDTO.Info> optionalMember = findByEmail(email);
        if (optionalMember.isPresent()) {
             validMemberResigned(optionalMember.get());
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

    @Override
    public int deleteMember(MemberDTO.Delete member) {
        SqlSession session = MySqlSessionFactory.getSession();
        int status = 0;
        try {
            status = new MemberDAO().deleteMember(session, member);
            session.commit();
        } finally {
            session.close();
        }
        return status;
    }

    @Override
    public void validMemberResigned(MemberDTO.Info member) {
        if (member.getAuthority() == MemberRole.RESIGN) {
            throw new InvalidValueException("탈퇴한 사용자입니다.\n 탈퇴 후 3일간 가입하실 수 없습니다.");
        }
    }

}
