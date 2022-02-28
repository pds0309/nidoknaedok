package com.errors.exception;

import com.errors.ErrorCode;

public class UserAccessDeniedException extends UpperCustomException {

    public UserAccessDeniedException(String message) {
        super(message, ErrorCode.ACCESS_DENIED);
    }
}
