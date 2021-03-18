package com.angelangelov.remont_bg.error.Offer;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Offer with this id does not exists")
public class OfferWithIdNotExists extends RuntimeException {
    public OfferWithIdNotExists() {
    }

    public OfferWithIdNotExists(String message) {
        super(message);
    }
}
