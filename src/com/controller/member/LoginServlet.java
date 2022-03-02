package com.controller.member;

import com.controller.common.JSONResponse;
import com.dto.member.MemberDTO;
import com.errors.exception.InvalidValueException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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

        MemberDTO.Info memberDTO = new MemberServiceImpl().login(signInDTO)
                .orElseThrow(() -> new InvalidValueException("아이디 또는 비밀번호가 올바르지 않습니다"));

        memberDTO.addSession(request.getSession());

        Map<String, String> map = new HashMap<>();
        map.put("result", "200");
        map.put("message", "로그인 성공");
        JSONResponse.send(response, map, 200);
    }
}
