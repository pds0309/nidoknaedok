package com.dto.member;

public enum SocialType {
    NAVER("naver"),
    KAKAO("kakao"),
    GOOGLE("google"),
    NORMAL("normal");

    private String name;

    SocialType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
