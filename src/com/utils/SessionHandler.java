package com.utils;

import com.errors.exception.UserAccessDeniedException;

import javax.servlet.http.HttpSession;

public class SessionHandler {

    private SessionHandler() {
        //
    }

    /**
     * 로그인으로 리다이렉트 로직 외에 프로세스에서 세션 검증이 필요할 때 검증 후 객체 리턴
     * throw: 세션 없음
     *
     * @param session   : 세션
     * @param attrName: : 세션이름
     * @param <T>       : 객체 타입
     * @return : 객체 반환
     */
    public static <T extends Object> T verify(HttpSession session, String attrName) {
        T t = (T) session.getAttribute(attrName);
        if (t == null) {
            throw new UserAccessDeniedException("접근 제한된 요청입니다");
        }
        return t;
    }
}
