package com.angelangelov.remont_bg.model.bindings;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

public class OfferEditBindingModel extends BaseBindingModel{

    private String name;
    private MultipartFile image;
    private BigDecimal price;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startsOn;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endsOn;
    private String ownerPhoneNumber;
    private String description;

    public OfferEditBindingModel() {
    }

    @NotBlank(message = "Това поле е задължително!")
    @Length(min = 3 ,max = 30,message = "Полето трябва да бъде минимум 3 символа и максимум 30")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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



    //TODO: ADD REGEX FOR PHONE NUMBER
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

}