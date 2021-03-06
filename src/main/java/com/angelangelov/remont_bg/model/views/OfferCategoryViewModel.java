package com.angelangelov.remont_bg.model.views;

import com.angelangelov.remont_bg.model.entities.Offer;
import com.angelangelov.remont_bg.model.entities.enums.ServiceOfferNames;
import com.angelangelov.remont_bg.model.services.BaseServiceModel;

import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import java.util.List;

public class OfferCategoryViewModel {
    private ServiceOfferNames name;
    private String description;

    private List<Offer> offers;

    public OfferCategoryViewModel() {
    }

    public ServiceOfferNames getName() {
        return name;
    }

    public void setName(ServiceOfferNames name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }
}
