package com.angelangelov.remont_bg.model.services;

import com.angelangelov.remont_bg.model.entities.Comment;
import com.angelangelov.remont_bg.model.entities.LogEntity;
import com.angelangelov.remont_bg.model.entities.enums.Region;
import com.fasterxml.jackson.databind.ser.Serializers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class OfferServiceModel  extends BaseServiceModel {
    private String name;
    private String description;
    private String image;
    private BigDecimal price;
    private LocalDate startsOn;
    private LocalDate endsOn;
    private Region region;
    private Boolean isActive;
    private Boolean isApproved;
    private String ownerPhoneNumber;
    private UserServiceModel user;
    private String category;
    private Set<LogEntity> logs;
    private List<CommentServiceModel> comments;

    public OfferServiceModel() {
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public LocalDate getStartsOn() {
        return startsOn;
    }

    public void setStartsOn(LocalDate startsOn) {
        this.startsOn = startsOn;
    }

    public LocalDate getEndsOn() {
        return endsOn;
    }

    public void setEndsOn(LocalDate endsOn) {
        this.endsOn = endsOn;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getApproved() {
        return isApproved;
    }

    public void setApproved(Boolean approved) {
        isApproved = approved;
    }

    public String getOwnerPhoneNumber() {
        return ownerPhoneNumber;
    }

    public void setOwnerPhoneNumber(String ownerPhoneNumber) {
        this.ownerPhoneNumber = ownerPhoneNumber;
    }

    public UserServiceModel getUser() {
        return user;
    }

    public void setUser(UserServiceModel user) {
        this.user = user;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<CommentServiceModel> getComments() {
        return comments;
    }

    public void setComments(List<CommentServiceModel> comments) {
        this.comments = comments;
    }

    public Set<LogEntity> getLogs() {
        return logs;
    }

    public void setLogs(Set<LogEntity> logs) {
        this.logs = logs;
    }
}
