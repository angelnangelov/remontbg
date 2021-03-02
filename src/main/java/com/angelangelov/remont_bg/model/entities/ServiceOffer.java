package com.angelangelov.remont_bg.model.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "services")
public class ServiceOffer extends BaseOffer {
    private BigDecimal price;
    private ServiceCategory category;

    public ServiceOffer() {
    }

    @ManyToOne
    public ServiceCategory getCategory() {
        return category;
    }

    public void setCategory(ServiceCategory category) {
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
