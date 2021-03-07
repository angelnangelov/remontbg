package com.angelangelov.remont_bg.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "tools")
public class ToolOffer extends BaseOffer {
    private BigDecimal price;

    private ToolCategory toolCategory;

    public ToolOffer() {
    }

    @ManyToOne
    public ToolCategory getToolCategory() {
        return toolCategory;
    }

    public void setToolCategory(ToolCategory toolCategory) {
        this.toolCategory = toolCategory;
    }

    @Column(nullable = false)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
