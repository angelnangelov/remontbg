package com.angelangelov.remont_bg.model.views;

import com.angelangelov.remont_bg.model.entities.enums.Region;

import java.math.BigDecimal;

public class OfferApiViewModel {
    private String name;
    private String description;
    private String image;
    private BigDecimal price;
    private String category;
    private Region region;

    public OfferApiViewModel() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
}
