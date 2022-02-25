package com.service.member;

import com.dto.member.MemberDTO;

public interface MemberService {
    int signUpMember(MemberDTO.SignUp member);

    long existCheckByName(String name);

    long existCheckByEmail(String email);
}
