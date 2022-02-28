package com.service.member;

import com.dto.member.MemberDTO;

import java.util.Optional;

public interface MemberService {
    int signUpMember(MemberDTO.SignUp member);

    Optional<MemberDTO.Info> findByEmail(String email);

    Optional<MemberDTO.Info> findByName(String name);

    void validInputName(String name);

    void validInputEmail(String email);
}
