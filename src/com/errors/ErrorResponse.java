package com.errors;

public class ErrorResponse {

    private int status;
    private String code;
    private String message;
    private String detail;

    private ErrorResponse() {
        //
    }

    private ErrorResponse(ErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    private ErrorResponse(ErrorCode errorCode, String detail) {
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.detail = detail;
    }

    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(errorCode);
    }

    public static ErrorResponse of(ErrorCode errorCode, String detail) {
        return new ErrorResponse(errorCode, detail);
    }
}
