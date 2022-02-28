package com.controller.common;

import com.errors.exception.UserAccessDeniedException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class Cookies {

    private Cookies() {
        //
    }

    public static Cookie get(HttpServletRequest request, String name) {
        return Arrays.stream(request.getCookies())
                .filter(cookie -> name.equals(cookie.getName()))
                .findFirst()
                .orElseThrow(
                        () -> new UserAccessDeniedException("인증 유효 정보가 만료되었거나 접근 권한이 없습니다.")
                );
    }
}
