package com.angelangelov.remont_bg.model.entities;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "offer_categories")
public class ServiceCategory extends BaseCategory{


    private List<ServiceOffer> services;

    public ServiceCategory() {
    }

    @OneToMany(mappedBy = "category")
    public List<ServiceOffer> getServices() {
        return services;
    }

    public void setServices(List<ServiceOffer> services) {
        this.services = services;
    }
}
