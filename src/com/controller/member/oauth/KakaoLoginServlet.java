package com.controller.member.oauth;


import com.config.openapi.ApiKey;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet("/members/oauth/kakao")
public class KakaoLoginServlet extends HttpServlet {
    private static final String KAKAO_AUTH = "https://kauth.kakao.com/oauth/token";
    private static final String KAKAO_INFO = "https://kapi.kakao.com/v2/user/me";

    /**
     * /members/kakao
     * GET
     * 카카오 OAuth 회원을 등록 요청을 한다.
     * code를 얻어와 토큰을 얻어오고 토큰을 통해 사용자 정보를 얻는다.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");

        String token = getAccessToken(code, request);
        String kakaoMemberInfo = getMemberInfoByToken(token);

        // TODO 요청한 경로로 리다이렉트 되어야 한다
        response.sendRedirect(request.getContextPath() + "/home");
    }

    /**
     * 요청으로부터 토큰을 얻는다
     *
     * @param code: 인증 요청으로부터 얻은 코드
     * @return 액세스 토큰
     */
    private String getAccessToken(String code, HttpServletRequest request) throws ServletException, IOException {
        URL url = new URL(KAKAO_AUTH);

        Map<String, Object> paramMap = new LinkedHashMap<>();
        paramMap.put("grant_type", "authorization_code");
        paramMap.put("client_id", "8861433d60e2a2021bb2d209868e868c");
        paramMap.put("client_secret", ApiKey.getInstance().getKakaoOAuthSecret());
        paramMap.put("redirect_uri", request.getRequestURL());
        paramMap.put("code", code);

        String postData = appendParamForRequest(paramMap);
        HttpURLConnection conn = setConnectionForRequestPOST(url, postData.getBytes(StandardCharsets.UTF_8));

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
        String accessToken = br.readLine().trim();
        br.close();

        return accessToken;
    }

    /**
     * 토큰 정보로 사용자 정보를 얻는다.
     *
     * @param accessToken 얻은 토큰
     */
    private String getMemberInfoByToken(String accessToken) throws ServletException, IOException {
        JSONObject tokenObject = new JSONObject(accessToken);
        URL url = new URL(KAKAO_INFO);


        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("client_secret", ApiKey.getInstance().getKakaoOAuthSecret());
        conn.setRequestProperty("Authorization", "Bearer " + tokenObject.getString("access_token"));
        conn.setDoOutput(true);

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
        String memberInfo = br.readLine().trim();
        br.close();

        return memberInfo;
    }

    private String appendParamForRequest(Map<String, Object> paramMap) throws IOException {
        StringBuilder postData = new StringBuilder();

        for (Map.Entry<String, Object> param : paramMap.entrySet()) {
            if (postData.length() != 0) {
                postData.append('&');
            }
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        return postData.toString();
    }

    private HttpURLConnection setConnectionForRequestPOST(URL url, byte[] postDataBytes) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);
        return conn;
    }
}
