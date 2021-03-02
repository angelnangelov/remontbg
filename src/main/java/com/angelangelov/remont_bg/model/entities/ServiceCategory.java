package com.angelangelov.remont_bg.model.entities;

import com.angelangelov.remont_bg.model.entities.enums.ServiceOfferNames;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "offer_categories")
public class ServiceCategory extends BaseEntity{
    private ServiceOfferNames name;
    private String description;

    private List<ServiceOffer> services;


    public ServiceCategory(ServiceOfferNames name, String description) {
        this.name=name;
        this.description=description;
    }

    public ServiceCategory() {

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
    @OneToMany(mappedBy = "category")
    public List<ServiceOffer> getServices() {
        return services;
    }

    public void setServices(List<ServiceOffer> services) {
        this.services = services;
    }
}
