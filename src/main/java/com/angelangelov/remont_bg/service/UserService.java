package com.angelangelov.remont_bg.service;

import com.angelangelov.remont_bg.model.services.OfferServiceModel;
import com.angelangelov.remont_bg.model.services.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserServiceModel findUserByUsername(String username);

    UserServiceModel register(UserServiceModel userServiceModel);

    boolean existByUsername(String username);

    boolean existByEmail(String email);
    UserServiceModel findUserById(String id);
    void setUserRole(String id, String role);
    List<UserServiceModel> findAllUsers();
    void updateProfile(UserServiceModel userServiceModel, String username);
    UserServiceModel changePassword(UserServiceModel userServiceModel, String oldPassword ,String username);



}
