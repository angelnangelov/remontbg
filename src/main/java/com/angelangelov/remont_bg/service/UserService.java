package com.angelangelov.remont_bg.service;

import com.angelangelov.remont_bg.model.services.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserServiceModel findUserByUsername(String username);

    void register(UserServiceModel userServiceModel);

    boolean existByUsername(String username);

    boolean existByEmail(String email);
    UserServiceModel findUserById(String id);
}
