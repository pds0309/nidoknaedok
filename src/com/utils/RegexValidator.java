package com.utils;

import com.errors.exception.InvalidValueException;

import java.util.regex.Pattern;

public class RegexValidator {
    private static final String REGEX_NAME = "^[가-힣a-zA-Z0-9_]{2,12}$";
    private static final String REGEX_EMAIL = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private static final String REGEX_PASSWORD = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d~!@#]{8,20}$";

    private static final String DETAIL_NAME = "이름은 문자,숫자로 구성된 2~12글자여야 합니다";
    private static final String DETAIL_EMAIL = "이메일 형식이 올바르지 않습니다";
    private static final String DETAIL_PASSWORD = "비밀번호는 영문 숫자 및 ~!@# 특수문자가 모두 포함된 8~20글자여야 합니다";

    public static void validate(String name, String email, String password) {
        validName(name);
        validPassword(password);
        validEmail(email);
    }

    public static void validate(String name, String password) {
        validName(name);
        validPassword(password);
    }

    public static void validName(String name) {
        if (name == null || !Pattern.matches(REGEX_NAME, name)) {
            throw new InvalidValueException(DETAIL_NAME);
        }
    }

    public static void validEmail(String email) {
        if (email == null || !Pattern.matches(REGEX_EMAIL, email)) {
            throw new InvalidValueException(DETAIL_EMAIL);
        }
    }

    public static void validPassword(String password) {
        if (password == null || !Pattern.matches(REGEX_PASSWORD, password)) {
            throw new InvalidValueException(DETAIL_PASSWORD);
        }
    }
}
