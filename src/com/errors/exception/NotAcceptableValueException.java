package com.errors.exception;

import com.errors.ErrorCode;

public class NotAcceptableValueException extends UpperCustomException {

    public NotAcceptableValueException(String message) {
        super(message, ErrorCode.CONFLICT_INPUT);
    }
}
