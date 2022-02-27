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
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet("/members/oauth/kakao")
public class KakaoLoginServlet extends HttpServlet {
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

        if (userNotCheckedEmailScope(tokenObject.getString("scope"))) {
            emailConsent(request, response);
            return;
        }

        String memberInfo = oauth2.getMemberInfoByToken(tokenObject.getString("access_token"));
        // TODO 요청한 경로로 리다이렉트 되어야 한다
        response.sendRedirect(request.getContextPath() + "/home");
    }

    /**
     * 이메일이 필수인 서비스라 동의를 다시 구해야 한다.
     */
    private void emailConsent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> paramMap = new LinkedHashMap<>();
        paramMap.put("client_id", "8861433d60e2a2021bb2d209868e868c");
        paramMap.put("redirect_uri", request.getRequestURL());
        paramMap.put("response_type", "code");
        paramMap.put("scope", "account_email");
        String postData = oauth2.appendParamForRequest(paramMap);

        response.sendRedirect(SocialOAuth2.KAKAO_ADDINFO + postData);
    }

    /**
     * 이메일 수집 동의를 했나 검사한다.
     * @param scope: 동의 정보 목록이다.
     * @return: 동의안했으면 true
     */
    private boolean userNotCheckedEmailScope(String scope) {
        return !scope.contains("account_email");
    }
}
