package com.angelangelov.remont_bg.error.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "User with this id does not exists")

public class UserWithIdNotExists extends RuntimeException {
    public UserWithIdNotExists() {
    }

    public UserWithIdNotExists(String message) {
        super(message);
    }
}
