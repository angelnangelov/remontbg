package com.angelangelov.remont_bg.error.comment;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Comment with this id does not exists")
public class CommentWithIdNotExists extends RuntimeException {
    public CommentWithIdNotExists() {
    }

    public CommentWithIdNotExists(String message) {
        super(message);
    }
}
