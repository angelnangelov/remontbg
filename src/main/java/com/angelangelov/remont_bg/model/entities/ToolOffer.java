package com.angelangelov.remont_bg.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "tools")
public class ToolOffer extends BaseOffer {
    private BigDecimal pricePerDay;
    private BigDecimal pricePerWeak;
    private BigDecimal pricePerMonth;
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

    @Column(name = "price_per_day", nullable = false)
    public BigDecimal getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(BigDecimal pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    @Column(name = "price_per_weak", nullable = false)
    public BigDecimal getPricePerWeak() {
        return pricePerWeak;
    }

    public void setPricePerWeak(BigDecimal pricePerWeak) {
        this.pricePerWeak = pricePerWeak;
    }

    @Column(name = "price_per_month", nullable = false)
    public BigDecimal getPricePerMonth() {
        return pricePerMonth;
    }

    public void setPricePerMonth(BigDecimal pricePerMonth) {
        this.pricePerMonth = pricePerMonth;
    }
}
