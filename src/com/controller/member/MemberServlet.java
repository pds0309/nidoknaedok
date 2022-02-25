package com.controller.member;

import com.controller.common.SendJSONResponse;
import com.dto.member.MemberDTO;
import com.errors.exception.InvalidValueException;
import com.errors.exception.NotAcceptableValueException;
import com.service.member.MemberServiceImpl;
import com.utils.PwdEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/members")
public class MemberServlet extends HttpServlet {
    /**
     * /members
     * POST
     * 회원을 등록하다
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = "";
        String password = "";
        String email = "";
        String address = "";
        String addressDetail = "";

        try {
            name = request.getParameter("name").trim();
            password = request.getParameter("password").trim();
            email = request.getParameter("email").trim();
            address = request.getParameter("address").trim();
            addressDetail = request.getParameter("addressdetail").trim();
            password = PwdEncoder.encrypt(password);
        } catch (NullPointerException e) {
            throw new InvalidValueException("회원등록 입력 정보 누락 식별");
        } catch (NoSuchAlgorithmException e) {
            InvalidValueException ex = new InvalidValueException("일부 입력 데이터 처리에 실패해 회원등록 실패");
            e.initCause(ex);
            throw ex;
        }

        int status = new MemberServiceImpl().signUpMember(
                new MemberDTO.SignUp(name, password, email, address, addressDetail));

        Map<String, Integer> map = new HashMap<>();
        map.put("result", status);
        SendJSONResponse.sendAsJson(response, map, 201);
    }
}
