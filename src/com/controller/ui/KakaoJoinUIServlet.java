package com.controller.ui;

import com.controller.common.Cookies;
import com.dto.oauth.KakaoUserDTO;
import com.errors.exception.UserAccessDeniedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.oauth.KakaoOAuth2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/kakaojoin")
public class KakaoJoinUIServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie accessTokenCookie = Cookies.get(request, "_katkauta")
                .orElseThrow(() -> new UserAccessDeniedException("인증 유효 정보가 만료되었거나 접근 권한이 없습니다."));

        String userInfo = new KakaoOAuth2().getMemberInfoByToken(accessTokenCookie.getValue());
        KakaoUserDTO kakaoUserDTO = new ObjectMapper().readValue(userInfo, KakaoUserDTO.class);
        request.setAttribute("kakaoinfo", kakaoUserDTO);

        HttpSession session = request.getSession();
        if (session.getAttribute("memid") == null) {
            request.getRequestDispatcher("components/kakaojoin.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("components/oauthinteg.jsp").forward(request, response);
        }
    }

}
