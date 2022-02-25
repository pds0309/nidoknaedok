package com.errors.exception;

import com.errors.ErrorCode;

public class UserNotFoundException extends UpperCustomException {

    public UserNotFoundException(String message) {
        super(message, ErrorCode.USER_REQUEST_NOT_FOUND);
    }
}
