package com.utils;

public final class Constants {

    private Constants() {
        //
    }

    public static final String AUTH_COOKIE_NAME = "_katkauta";
    public static final int AUTH_COOKIE_TIMEOUT = 60 * 20;
    public static final int NORMAL_COOKIE_TIMEOUT = 60 * 60 * 6;
    public static final String RESIGN_MEM_PROFILE = "https://yourdokzone.s3.ap-northeast-2.amazonaws.com/resign.JPG";
    public static final int RESIGN_DATE = 3;
    public static final String CURRENT_MEMBER_SESSION_NAME = "meminfo";
}
