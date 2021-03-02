package com.angelangelov.remont_bg.error.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "User with this username does not exists")
public class UserWithUsernameNotExists extends RuntimeException {

    public UserWithUsernameNotExists() {
    }

    public UserWithUsernameNotExists(String message) {
        super(message);
    }
}

