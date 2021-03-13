package com.angelangelov.remont_bg.model.entities;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "offers")
public class Offer extends BaseOffer {
    private BigDecimal price;
    private OfferCategory category;
    private List<Comment> comments;


    public Offer() {
    }


    @ManyToOne
    public OfferCategory getCategory() {
        return category;
    }

    public void setCategory(OfferCategory category) {
        this.category = category;
    }

    @Column(name = "price", nullable = false)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    @OneToMany(mappedBy = "offer",fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
