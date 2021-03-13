package com.angelangelov.remont_bg.model.services;

import com.angelangelov.remont_bg.model.entities.Offer;
import com.angelangelov.remont_bg.model.entities.ToolOffer;
import com.angelangelov.remont_bg.model.entities.User;

import java.time.LocalDateTime;

public class CommentServiceModel extends BaseServiceModel {
    private UserServiceModel user;
    private String description;
    private LocalDateTime postedTime = LocalDateTime.now();
    private OfferServiceModel offer;


    public UserServiceModel getUser() {
        return user;
    }

    public void setUser(UserServiceModel user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getPostedTime() {
        return postedTime;
    }

    public void setPostedTime(LocalDateTime postedTime) {
        this.postedTime = postedTime;
    }

    public OfferServiceModel getOffer() {
        return offer;
    }

    public void setOffer(OfferServiceModel offer) {
        this.offer = offer;
    }


}
