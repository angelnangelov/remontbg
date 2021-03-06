package com.angelangelov.remont_bg.model.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "services")
public class Offer extends BaseOffer {
    private BigDecimal price;
    private OfferCategory category;

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
}
