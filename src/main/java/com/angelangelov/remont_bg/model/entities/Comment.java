package com.angelangelov.remont_bg.model.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
@Entity
@Table(name = "comments")
public class Comment extends BaseEntity {
    private User user;
    private String description;
    private LocalDateTime postedTime = LocalDateTime.now();
    private Offer offer;


    public Comment() {
    }

    @ManyToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @ManyToOne
    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public LocalDateTime getPostedTime() {
        return postedTime;
    }

    public void setPostedTime(LocalDateTime postedTime) {
        this.postedTime = postedTime;
    }
}
