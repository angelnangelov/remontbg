package com.angelangelov.remont_bg.error.Offer;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Tool with this id does not exists")
public class ToolWithIdNotExists extends RuntimeException {
    public ToolWithIdNotExists() {
    }

    public ToolWithIdNotExists(String message) {
        super(message);
    }
}
