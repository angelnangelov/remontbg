package com.angelangelov.remont_bg.model.bindings;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import static com.angelangelov.remont_bg.constants.Regex.PHONE_REGEX;

public class ToolOfferAddBindingModel extends BaseBindingModel{
    private String name;
    private String brand;
    private String model;
    private MultipartFile image;
    private BigDecimal price;
    private BigDecimal deposit;
    private String power;
    private String category;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startsOn;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endsOn;
    private String region;
    private String ownerPhoneNumber;
    private String description;


    public ToolOfferAddBindingModel() {
    }

    @NotBlank(message = "Това поле е задължително!")
    @Length(min = 3 ,max = 30,message = "Полето трябва да бъде минимум 3 символа и максимум 30")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @NotBlank(message = "Това поле е задължително!")
    @Length(min = 3 ,max = 30,message = "Полето трябва да бъде минимум 3 символа и максимум 30")
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
    @NotBlank(message = "Това поле е задължително!")
    @Length(min = 3 ,max = 30,message = "Полето трябва да бъде минимум 3 символа и максимум 30")
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    @NotNull(message = "Това поле е задължително!")
    @Positive(message = "Цената не може да бъде отрицателно число")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    @NotBlank(message = "Това поле е задължително!")
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @FutureOrPresent(message = "Обявата не може да започва в миналото!")
    public LocalDate getStartsOn() {
        return startsOn;
    }

    public void setStartsOn(LocalDate startsOn) {
        this.startsOn = startsOn;
    }
    @FutureOrPresent(message = "Обявата не може да свършва в миналото!")
    public LocalDate getEndsOn() {
        return endsOn;
    }

    public void setEndsOn(LocalDate endsOn) {
        this.endsOn = endsOn;
    }
    @NotBlank(message = "Това поле е задължително!")
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }


    @Length(min = 7,max = 13,message = "Моля въведете валиден телефонен номер")
    @Pattern(regexp = PHONE_REGEX,message = "Моля въведете валиден телефонен номер")
    @NotBlank(message = "Това поле е задължително!")
    public String getOwnerPhoneNumber() {
        return ownerPhoneNumber;
    }

    public void setOwnerPhoneNumber(String ownerPhoneNumber) {
        this.ownerPhoneNumber = ownerPhoneNumber;
    }
    @NotBlank(message = "Това поле е задължително!")
    @Length(min = 5, message = "Минимум 5 символа")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @NotNull(message = "Това поле е задължително!")
    @Positive(message = "Цената не може да бъде отрицателно число")    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    @Length(min = 5,max = 15,message = "Може да е междъ 5 и 15 символа")
    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }
}
