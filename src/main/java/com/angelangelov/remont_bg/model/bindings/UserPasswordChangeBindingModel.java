package com.angelangelov.remont_bg.model.bindings;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class UserPasswordChangeBindingModel {
    private String oldPassword;
    private String password;
    private String confirmPassword;

    public UserPasswordChangeBindingModel() {

    }
    @Length(min = 6, max = 20, message = "За вашата сигурност паролата трябва да е минимум 6 символа" )
    @NotNull(message = "Това поле е задължително!")
    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    @Length(min = 6, max = 20, message = "За вашата сигурност паролата трябва да е минимум 6 символа" )
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
}
