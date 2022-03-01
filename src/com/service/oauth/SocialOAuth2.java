package com.service.oauth;

import javax.servlet.ServletException;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public interface SocialOAuth2 {
    String X_WWW_FORM_TYPE = "application/x-www-form-urlencoded;charset=utf-8";
    String KAKAO_AUTH = "https://kauth.kakao.com/oauth/token";
    String KAKAO_INFO = "https://kapi.kakao.com/v2/user/me";
    String KAKAO_ADDINFO = "https://kauth.kakao.com/oauth/authorize?";

    /**
     * 요청으로부터 토큰을 얻는다
     *
     * @param authorizedCode: 요청에서 얻는 코드
     * @param requestURL:     리다이렉트 경로
     * @return 액세스 토큰 뭉탱이
     */
    String getAccessToken(String authorizedCode, Object requestURL) throws IOException;

    /**
     * 토큰으로 사용자 정보를 얻는다
     *
     * @param accessToken: 토큰
     * @return 해당 사용자 정보
     */
    String getMemberInfoByToken(String accessToken) throws ServletException, IOException;

    String appendParamForRequest(Map<String, Object> paramMap) throws IOException;

    static String getReaderResult(BufferedReader br) throws IOException {
        StringBuilder sb = new StringBuilder();
        String result = "";
        while ((result = br.readLine()) != null) {
            sb.append(result);
        }
        br.close();
        return sb.toString();
    }
}
