package com.angelangelov.remont_bg.model.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "tools")
public class ToolOffer extends BaseOffer {
    private BigDecimal price;
    private String power;
    private BigDecimal deposit;
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

    @Column
    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    @Column(nullable = false)
    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }


}
