package com.angelangelov.remont_bg.model.services;

import com.angelangelov.remont_bg.model.entities.Offer;
import com.angelangelov.remont_bg.model.entities.User;

import java.time.LocalDateTime;

public class LogServiceModel {
    private UserServiceModel user;
    private OfferServiceModel offer;
    private String action;
    private LocalDateTime dateTime;

    public LogServiceModel() {
    }

    public UserServiceModel getUser() {
        return user;
    }

    public void setUser(UserServiceModel user) {
        this.user = user;
    }

    public OfferServiceModel getOffer() {
        return offer;
    }

    public void setOffer(OfferServiceModel offer) {
        this.offer = offer;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
