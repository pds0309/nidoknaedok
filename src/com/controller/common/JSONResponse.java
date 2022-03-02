package com.controller.common;

import com.dto.common.ResponseDTO;
import com.errors.ErrorCode;
import com.errors.ErrorResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 결과를 JSON 데이터로 응답하는 메소드가 있다.
 */
public class JSONResponse {

    private static final String CONTENT_TYPE = "application/json;charset=utf-8";

    public static <T> void send(HttpServletResponse response, T data, int status) throws IOException {
        response.setContentType(CONTENT_TYPE);
        response.setStatus(status);
        PrintWriter out = response.getWriter();
        out.print(ResponseDTO.toJson(status, data));
        out.flush();
    }

    public static void send(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setContentType(CONTENT_TYPE);
        response.setStatus(errorCode.getStatus());
        PrintWriter out = response.getWriter();
        out.print(ResponseDTO.toJson(ErrorResponse.of(errorCode)));
        out.flush();
    }

    public static void send(HttpServletResponse response, ErrorCode errorCode, String message) throws IOException {
        response.setContentType(CONTENT_TYPE);
        response.setStatus(errorCode.getStatus());
        PrintWriter out = response.getWriter();
        out.print(ResponseDTO.toJson(ErrorResponse.of(errorCode, message)));
        out.flush();
    }
}
