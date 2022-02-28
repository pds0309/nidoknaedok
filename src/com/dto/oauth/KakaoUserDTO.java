package com.dto.oauth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * 카카오 멤버 가입을 위한 전달 객체
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoUserDTO {
    private long id;
    private PropsDetail properties;
    private AccountDetail kakaoAccount;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public class PropsDetail {
        private String nickname;
        private String thumbnailImage;

        public String getNickname() {
            return nickname;
        }

        public String getThumbnailImage() {
            return thumbnailImage;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class AccountDetail {
        private String email;

        public String getEmail() {
            return email;
        }
    }

    public long getId() {
        return id;
    }

    public PropsDetail getProperties() {
        return properties;
    }

    public AccountDetail getKakaoAccount() {
        return kakaoAccount;
    }
}
