package com.angelangelov.remont_bg.model.entities;

import com.angelangelov.remont_bg.model.entities.enums.Region;

import javax.persistence.*;
import java.time.LocalDate;
@MappedSuperclass
abstract class BaseOffer extends BaseEntity{
    private String name;
    private String description;
    private String image;
    private LocalDate startsOn;
    private LocalDate endsOn;
    private Region region;
    private Boolean isActive;
    private Boolean isApproved;
    private String ownerPhoneNumber;
    private User user;


    public BaseOffer() {
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Column(name = "description", nullable = false, columnDefinition = "text")
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    @Column(name = "image_url")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Column(name = "starts_on", nullable = false)
    public LocalDate getStartsOn() {
        return startsOn;
    }

    public void setStartsOn(LocalDate startsOn) {
        this.startsOn = startsOn;
    }

    @Column(name = "ends_on", nullable = false)
    public LocalDate getEndsOn() {
        return endsOn;
    }

    public void setEndsOn(LocalDate endsOn) {
        this.endsOn = endsOn;
    }



    @Enumerated(EnumType.STRING)
    @Column(name = "region", nullable = false)
    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    @Column(name = "is_active")
    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Column(name = "is_approved")
    public Boolean getApproved() {
        return isApproved;
    }

    public void setApproved(Boolean approved) {
        isApproved = approved;
    }

    @Column(name = "owner_phone_number", nullable = false)
    public String getOwnerPhoneNumber() {
        return ownerPhoneNumber;
    }
    public void setOwnerPhoneNumber(String ownerPhoneNumber) {
        this.ownerPhoneNumber = ownerPhoneNumber;
    }
    @ManyToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
