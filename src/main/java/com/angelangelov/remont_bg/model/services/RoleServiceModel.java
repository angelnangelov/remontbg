package com.angelangelov.remont_bg.model.services;

public class RoleServiceModel extends BaseServiceModel{
    private String authority;

    public RoleServiceModel() {
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
