package com.angelangelov.remont_bg.service;

import com.angelangelov.remont_bg.model.bindings.OfferAddBindingModel;
import com.angelangelov.remont_bg.model.services.OfferServiceModel;

public interface OfferService {
    void save(OfferServiceModel offer, String username);

}
