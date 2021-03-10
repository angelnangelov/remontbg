package com.angelangelov.remont_bg.error.category;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Category with this id does not exists")
public class CategoryWithIdNotExists extends RuntimeException {
    public CategoryWithIdNotExists() {
    }

    public CategoryWithIdNotExists(String message) {
        super(message);
    }
}
