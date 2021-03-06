package com.angelangelov.remont_bg.model.bindings;


import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.angelangelov.remont_bg.constants.Regex.*;


public class    UserRegisterBindingModel extends BaseBindingModel {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String profileImageUrl;
    private String confirmPassword;

    public UserRegisterBindingModel() {
    }

    @Length(min = 3, max = 15, message = "Потребителското име трябва да бъде минимум 3 символа!")
    @Pattern(regexp = SIMPLE_TEXT_REGEX,message = "Потребителското име може да съдържа само букви и цифри")
    @NotBlank(message = "Това поле е задължително!")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Length(min = 2, max = 15, message = "Името Ви не може да бъде под 2 символа!")
    @Pattern(regexp = FIRST_NAME_REGEX, message = "Моля въведете валидно име!")
    @NotBlank(message = "Това поле е задължително!")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Length(min = 2, max = 15, message = "Фамилията Ви не може да бъде под 2 символа!")
    @Pattern(regexp = LAST_NAME_REGEX, message = "Моля въведете валидно име!")
    @NotBlank(message = "Това поле е задължително!")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Length(min = 5, max = 35, message = "Емайлът не може да бъде под 5 символа")
    @Pattern(regexp = EMAIL_REGEX, message = "Моля въведете валиден емайл адрес")
    @NotBlank(message = "Това поле е задължително!")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Length(min = 6, max = 30, message = "За вашата сигурност паролата трябва да е минимум 6 символа" )
    @NotNull(message = "Това поле е задължително!")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @Length(min = 6, max = 20, message = "За вашата сигурност паролата трябва да е минимум 6 символа" )
    @NotNull(message = "Това поле е задължително!")
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
