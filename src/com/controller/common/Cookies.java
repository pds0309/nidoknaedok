package com.controller.common;

import com.errors.exception.UserAccessDeniedException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

public class Cookies {

    private Cookies() {
        //
    }

    public static Optional<Cookie> get(HttpServletRequest request, String name) {
        return Arrays.stream(request.getCookies())
                .filter(cookie -> name.equals(cookie.getName()))
                .findFirst();
    }
}
