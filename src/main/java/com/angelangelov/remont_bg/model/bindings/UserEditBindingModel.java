package com.angelangelov.remont_bg.model.bindings;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserEditBindingModel {
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String city;
    private String profileImageUrl;
    private String oldPassword;
    private String password;
    private String confirmPassword;



    public UserEditBindingModel() {
    }


    @Email(message = "Моля въведете валиден емайл адрес")
    @NotBlank(message = "Това поле е задължително!")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Length(min = 2, max = 25, message = "Името Ви не може да бъде под 2 символа!")
    @NotBlank(message = "Това поле е задължително!")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    @Length(min = 2, max = 25, message = "Името Ви не може да бъде под 2 символа!")
    @NotBlank(message = "Това поле е задължително!")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Length(min = 10,max = 10,message = "Моля въведете валиден телефонен номер")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
    @Length(min = 2,max = 20,message = "Моля въведете валиден град номер")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    @Length(min = 6, max = 20, message = "За вашата сигурност паролата трябва да е минимум 6 символа" )
    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
    @Length(min = 6, max = 20, message = "За вашата сигурност паролата трябва да е минимум 6 символа" )
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    @Length(min = 6, max = 20, message = "За вашата сигурност паролата трябва да е минимум 6 символа" )
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
