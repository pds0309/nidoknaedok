package com.errors.exception;

import com.errors.ErrorCode;

public class UpperCustomException extends RuntimeException {

    private final ErrorCode errorCode;

    public UpperCustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public UpperCustomException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
