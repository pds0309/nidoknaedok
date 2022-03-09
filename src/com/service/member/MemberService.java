package com.service.member;

import com.dto.member.MemberDTO;

import java.util.Optional;

public interface MemberService {
    int signUpMember(MemberDTO.SignUp member);

    Optional<MemberDTO.Info> findByEmail(String email);

    Optional<MemberDTO.Info> findByName(String name);

    Optional<MemberDTO.Info> findById(long id);

    MemberDTO.Info login(MemberDTO.SignIn member);

    void validInputName(String name);

    void validInputEmail(String email);

    int updateMember(MemberDTO.Update member);

    int deleteMember(MemberDTO.Delete member);

    void validMemberResigned(MemberDTO.Info member);

}
