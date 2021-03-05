package com.angelangelov.remont_bg.error.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Old password is wrong")
public class UserOldPasswordNotCorrectException extends RuntimeException {


    public UserOldPasswordNotCorrectException() {

    }

    public UserOldPasswordNotCorrectException(String message) {
        super(message);

    }
}