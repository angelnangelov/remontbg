package com.angelangelov.remont_bg.model.bindings;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class UserPasswordChangeBindingModel {
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;

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
    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    @Length(min = 6, max = 20, message = "За вашата сигурност паролата трябва да е минимум 6 символа" )
    @NotNull(message = "Това поле е задължително!")
    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }
}
