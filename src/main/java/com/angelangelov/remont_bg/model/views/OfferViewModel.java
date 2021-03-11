package com.angelangelov.remont_bg.model.views;

import com.angelangelov.remont_bg.model.entities.enums.Region;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OfferViewModel {

    private String name;
    private String description;
    private String image;
    private BigDecimal price;
    private Region region;
    private LocalDate endsOn;
    private String ownerPhoneNumber;
    private OfferCategoryViewModel category;

    public OfferViewModel() {
    }


    public LocalDate getEndsOn() {
        return endsOn;
    }

    public void setEndsOn(LocalDate endsOn) {
        this.endsOn = endsOn;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String getOwnerPhoneNumber() {
        return ownerPhoneNumber;
    }

    public void setOwnerPhoneNumber(String ownerPhoneNumber) {
        this.ownerPhoneNumber = ownerPhoneNumber;
    }

    public OfferCategoryViewModel getCategory() {
        return category;
    }

    public void setCategory(OfferCategoryViewModel category) {
        this.category = category;
    }
}