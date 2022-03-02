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

@WebServlet("/auth/name")
public class CheckNameServlet extends HttpServlet {
    /**
     * /auth/name
     * GET
     * 회원 가입 또는 수정에서 이름을 검증하다
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        RegexValidator.validName(name);

        new MemberServiceImpl().validInputName(name);

        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        JSONResponse.send(response, map, response.getStatus());
    }

}
