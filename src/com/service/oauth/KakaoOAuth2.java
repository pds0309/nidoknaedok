package com.service.oauth;

import com.config.openapi.ApiKey;

import javax.servlet.ServletException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class KakaoOAuth2 implements SocialOAuth2 {

    private final ApiKey key = ApiKey.getInstance();

    @Override
    public String getAccessToken(String authorizedCode, Object requestURL) throws IOException {
        URL url = new URL(KAKAO_AUTH);

        Map<String, Object> paramMap = new LinkedHashMap<>();
        paramMap.put("grant_type", "authorization_code");
        paramMap.put("client_id", key.getKakaoOAuthId());
        paramMap.put("client_secret", key.getKakaoOAuthSecret());
        paramMap.put("redirect_uri", requestURL);
        paramMap.put("code", authorizedCode);

        String postData = appendParamForRequest(paramMap);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", X_WWW_FORM_TYPE);
        conn.setDoOutput(true);
        conn.getOutputStream().write(postData.getBytes(StandardCharsets.UTF_8));

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
        return SocialOAuth2.getReaderResult(br);
    }

    @Override
    public String getMemberInfoByToken(String accessToken) throws ServletException, IOException {
        URL url = new URL(KAKAO_INFO);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("client_secret", key.getKakaoOAuthSecret());
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);
        conn.setDoOutput(true);

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
        String memberInfo = br.readLine().trim();
        br.close();
        return memberInfo;
    }

    @Override
    public String appendParamForRequest(Map<String, Object> paramMap) throws IOException {
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
}
