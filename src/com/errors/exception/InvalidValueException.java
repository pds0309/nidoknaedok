package com.errors.exception;

import com.errors.ErrorCode;

public class InvalidValueException extends UpperCustomException {

    public InvalidValueException(String message) {
        super(message, ErrorCode.INVALID_INPUT);
    }
}
