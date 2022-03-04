package com.controller.ui;

import com.controller.common.Cookies;
import com.dto.oauth.KakaoUserDTO;
import com.errors.exception.UserAccessDeniedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.oauth.KakaoOAuth2;
import com.utils.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/kakaojoin")
public class KakaoJoinUIServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie accessTokenCookie = Cookies.get(request, Constants.AUTH_COOKIE_NAME).orElse(null);
        if (accessTokenCookie == null) {
            response.sendRedirect("login");
            return;
        }
        String userInfo = new KakaoOAuth2().getMemberInfoByToken(accessTokenCookie.getValue());
        KakaoUserDTO kakaoUserDTO = new ObjectMapper().readValue(userInfo, KakaoUserDTO.class);

        request.setAttribute("kakaoinfo",kakaoUserDTO);
        request.getRequestDispatcher("home").forward(request, response);
    }
}
