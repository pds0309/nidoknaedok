package com.controller.member.auth;

import com.controller.common.JSONResponse;
import com.service.member.MemberServiceImpl;
import com.utils.RegexValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/auth/email")
public class CheckEmailServlet extends HttpServlet {
    /**
     * /auth/email
     * GET
     * 회원 가입에서 이메일을 검증하다
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        RegexValidator.validEmail(email);

        new MemberServiceImpl().validInputEmail(email);

        Map<String, Object> map = new HashMap<>();
        map.put("email", email);
        JSONResponse.send(response, map, response.getStatus());
    }

}
