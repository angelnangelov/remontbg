package com.angelangelov.remont_bg.service;

import com.angelangelov.remont_bg.model.bindings.OfferAddBindingModel;
import com.angelangelov.remont_bg.model.entities.Offer;
import com.angelangelov.remont_bg.model.services.OfferCategoryServiceModel;
import com.angelangelov.remont_bg.model.services.OfferServiceModel;

import java.util.List;

public interface OfferService {
    void save(OfferServiceModel offer, String username);

    OfferServiceModel findById(String id);
    List<OfferServiceModel> findAllOffers();

    

    void approveOffer(String id);


    void deleteOffer(String id);
}
