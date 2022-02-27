package com.controller.member.oauth;


import com.service.oauth.KakaoOAuth2;
import com.service.oauth.SocialOAuth2;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet("/members/oauth/kakao")
public class KakaoLoginServlet extends HttpServlet {
    private static final String KAKAO_AUTH = "https://kauth.kakao.com/oauth/token";
    private static final String KAKAO_INFO = "https://kapi.kakao.com/v2/user/me";
    private static final String KAKAO_ADDINFO = "https://kauth.kakao.com/oauth/authorize?";
    private static final SocialOAuth2 oauth2 = new KakaoOAuth2();

    /**
     * /members/kakao
     * GET
     * 카카오 OAuth 회원을 등록 요청을 한다.
     * code를 얻어와 토큰을 얻어오고 토큰을 통해 사용자 정보를 얻는다.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");

        JSONObject tokenObject = new JSONObject(oauth2.getAccessToken(code, request.getRequestURL()));

        String memberInfo = oauth2.getMemberInfoByToken(tokenObject.getString("access_token"));
        // TODO 요청한 경로로 리다이렉트 되어야 한다
        response.sendRedirect(request.getContextPath() + "/home");
    }
}
