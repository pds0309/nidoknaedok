package com.controller.member;

import com.controller.common.JSONResponse;
import com.dto.member.MemberDTO;
import com.errors.exception.InvalidValueException;
import com.errors.exception.UserAccessDeniedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.member.MemberService;
import com.service.member.MemberServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/members/login")
public class LoginServlet extends HttpServlet {
    private static final MemberService memberService = new MemberServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (request.getHeader("referer") == null) {
            throw new UserAccessDeniedException("잘못된 경로에서의 접근 식별");
        }
        BufferedReader bufferedReader = request.getReader();
        String read = bufferedReader.readLine();
        if (read == null || "".equals(read)) {
            throw new InvalidValueException("회원 정보 누락 식별됨");
        }
        bufferedReader.close();
        MemberDTO.SignIn signInDTO = null;
        try {
            signInDTO = new ObjectMapper().readValue(read, MemberDTO.SignIn.class);
        } catch (Exception e) {
            throw new InvalidValueException("잘못된 로그인 입력 식별");
        }
        signInDTO.encPassword();

        MemberDTO.Info memberDTO = memberService.login(signInDTO);

        memberDTO.addSession(request.getSession());

        Map<String, String> map = new HashMap<>();
        map.put("status", "200");
        map.put("message", "로그인 성공");
        JSONResponse.send(response, map, 200);
    }
}
