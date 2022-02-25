package com.utils;

import java.text.MessageFormat;
import java.util.regex.Pattern;

public class RegexValidator {
    private static final String REGEX_NAME = "^[가-힣a-zA-Z0-9_]{2,12}$";
    private static final String REGEX_EMAIL = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private static final String REGEX_PASSWORD = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d~!@#]{8,20}$";

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
        if (!Pattern.matches(REGEX_NAME, name)) {
            throw new IllegalArgumentException(MessageFormat.format("Invalid MemberName: {0}", name));
        }
    }

    public static void validEmail(String email) {
        if (!Pattern.matches(REGEX_EMAIL, email)) {
            throw new IllegalArgumentException(MessageFormat.format("Invalid Email: {0}", email));
        }
    }

    public static void validPassword(String password) {
        if (!Pattern.matches(REGEX_PASSWORD, password)) {
            throw new IllegalArgumentException(MessageFormat.format("Invalid Password: {0}", password));
        }
    }
}
