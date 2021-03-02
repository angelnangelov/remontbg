package com.angelangelov.remont_bg.service.impl;

import com.angelangelov.remont_bg.error.user.UserWithIdNotExists;
import com.angelangelov.remont_bg.error.user.UserWithUsernameNotExists;
import com.angelangelov.remont_bg.model.entities.Role;
import com.angelangelov.remont_bg.model.entities.User;
import com.angelangelov.remont_bg.model.services.UserServiceModel;
import com.angelangelov.remont_bg.repository.UserRepository;
import com.angelangelov.remont_bg.service.RoleService;
import com.angelangelov.remont_bg.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.LinkedHashSet;

@Service
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RoleService roleService;

    private final PasswordEncoder encoder;

  @Autowired
    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository, RoleService roleService, PasswordEncoder encoder) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.encoder = encoder;
    }

    @Override
    public UserServiceModel findUserByUsername(String username) {
        User user = this.userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UserWithUsernameNotExists("User with this username does not exist!"));

        return this.modelMapper.map(user, UserServiceModel.class);    }

    @Override
    public void register(UserServiceModel userServiceModel) {
        if (userRepository.count() == 0) {
            roleService.seedRoles();
            userServiceModel.setAuthorities(roleService.findAll());
        } else {
            userServiceModel.setAuthorities(new LinkedHashSet<>());
            userServiceModel.getAuthorities().add(roleService.findByAuthority("ROLE_USER"));
        }

        User user = modelMapper.map(userServiceModel, User.class);
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public boolean existByUsername(String username) {
        return this.userRepository.existsByUsername(username);
    }

    @Override
    public boolean existByEmail(String email) {
        return this.userRepository.existsByEmail(email);    }


    @Override
    public UserServiceModel findUserById(String id) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new UserWithIdNotExists("User with this id does not exist!"));
        return this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with NOT FOUND  username: %s", username)));
    }
}
