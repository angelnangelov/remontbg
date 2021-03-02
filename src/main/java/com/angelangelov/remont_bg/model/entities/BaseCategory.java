package com.angelangelov.remont_bg.model.entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
abstract class BaseCategory extends BaseEntity{
    private String name;
    private String description;

    public BaseCategory() {
    }

    @Column(name = "category_name", nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description", nullable = false, unique = true, columnDefinition = "text")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
