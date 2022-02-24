package com.service.member;

import com.dto.member.MemberDTO;

public interface MemberService {
    int signUpMember(MemberDTO.SignUp member);
}
