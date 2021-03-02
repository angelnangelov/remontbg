package com.angelangelov.remont_bg.service;

import com.angelangelov.remont_bg.model.entities.Role;
import com.angelangelov.remont_bg.model.services.RoleServiceModel;

import java.util.Set;

public interface RoleService {
    void seedRoles();

    Set<RoleServiceModel> findAll();

    RoleServiceModel findByAuthority(String authority);
}
