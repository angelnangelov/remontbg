package com.angelangelov.remont_bg.model.entities;

import com.angelangelov.remont_bg.model.entities.enums.ServiceOfferNames;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "offer_categories")
public class OfferCategory extends BaseEntity{
    private ServiceOfferNames name;
    private String description;

    private List<Offer> offers;


    public OfferCategory(ServiceOfferNames name, String description) {
        this.name=name;
        this.description=description;
    }

    public OfferCategory() {

    }

    @Enumerated(EnumType.STRING)
    @Column( nullable = false, unique = true)
    public ServiceOfferNames getName() {
        return name;
    }

    public void setName(ServiceOfferNames name) {
        this.name = name;
    }

    @Column(name = "description", nullable = false, unique = true, columnDefinition = "text")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @OneToMany(mappedBy = "category",fetch = FetchType.EAGER)
    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }
}
