package com.config.openapi;

import org.apache.ibatis.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public class ApiKey {

    private static final ApiKey apiKey = new ApiKey();

    private String kakaoOAuthSecret = "";
    private String kakaoOAuthId = "";
    private String naverApiId = "";
    private String naverApiSecret = "";

    private ApiKey() {
        Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
        try {
            logger.info("SET API KEY SUCCESS");
            Properties properties = new Properties();
            properties.load(Resources.getResourceAsReader("com/config/apikey.properties"));

            this.kakaoOAuthSecret = properties.getProperty("kakao.oauth.secret");
            this.kakaoOAuthId = properties.getProperty("kakao.oauth.id");
            this.naverApiId = properties.getProperty("naver.api.id");
            this.naverApiSecret = properties.getProperty("naver.api.secret");
        } catch (IOException e) {
            logger.info("SET API KEY FAIL");
            e.printStackTrace();
        }
    }

    public static ApiKey getInstance() {
        return apiKey;
    }

    public String getKakaoOAuthSecret() {
        return kakaoOAuthSecret;
    }

    public String getKakaoOAuthId() {
        return kakaoOAuthId;
    }

    public String getNaverApiId() {
        return naverApiId;
    }

    public String getNaverApiSecret() {
        return naverApiSecret;
    }
}
