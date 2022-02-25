package com.service.member;

import com.dto.member.MemberDTO;

public interface MemberService {
    int signUpMember(MemberDTO.SignUp member);

    MemberDTO.Info existCheckByName(String name);

    MemberDTO.Info existCheckByEmail(String email);
}
