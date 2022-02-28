package com.controller.ui;

import com.controller.common.Cookies;
import com.dto.oauth.KakaoUserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.oauth.KakaoOAuth2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/kakaojoin")
public class KakaoJoinUIServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie accessTokenCookie = Cookies.get(request, "_katkauta");

        String userInfo = new KakaoOAuth2().getMemberInfoByToken(accessTokenCookie.getValue());
        KakaoUserDTO kakaoUserDTO = new ObjectMapper().readValue(userInfo, KakaoUserDTO.class);

        request.setAttribute("kakaoinfo", kakaoUserDTO);
        request.getRequestDispatcher("components/kakaojoin.jsp").forward(request, response);
    }
}
