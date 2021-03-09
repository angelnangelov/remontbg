package com.angelangelov.remont_bg.model.services;

import java.util.List;

public class OfferCategoryServiceModel extends BaseServiceModel {


    private String name;
    private String description;
    private List<OfferServiceModel> offers;

    public OfferCategoryServiceModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<OfferServiceModel> getOffers() {
        return offers;
    }

    public void setOffers(List<OfferServiceModel> offers) {
        this.offers = offers;
    }
}
