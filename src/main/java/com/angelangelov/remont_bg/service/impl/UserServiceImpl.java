package com.angelangelov.remont_bg.service.impl;

import com.angelangelov.remont_bg.error.user.UserOldPasswordNotCorrectException;
import com.angelangelov.remont_bg.error.user.UserWithIdNotExists;
import com.angelangelov.remont_bg.error.user.UserWithUsernameNotExists;
import com.angelangelov.remont_bg.model.entities.User;
import com.angelangelov.remont_bg.model.services.UserServiceModel;
import com.angelangelov.remont_bg.repository.UserRepository;
import com.angelangelov.remont_bg.service.EmailService;
import com.angelangelov.remont_bg.service.RoleService;
import com.angelangelov.remont_bg.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final EmailService emailService;
    private final PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository, RoleService roleService, EmailService emailService, PasswordEncoder encoder) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.emailService = emailService;
        this.encoder = encoder;
    }

    @Override
    public UserServiceModel findUserByUsername(String username) {
        User user = this.userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UserWithUsernameNotExists("User with this username does not exist!"));

        return this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public UserServiceModel register(UserServiceModel userServiceModel) {
        if (userRepository.count() == 0) {
            roleService.seedRoles();
            userServiceModel.setAuthorities(roleService.findAll());
        } else {
            userServiceModel.setAuthorities(new LinkedHashSet<>());
            userServiceModel.getAuthorities().add(roleService.findByAuthority("ROLE_USER"));
        }

        User user = modelMapper.map(userServiceModel, User.class);
        user.setPassword(encoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        this.emailService.sendSimpleMessage
                (savedUser.getEmail()
                        ,"Добре дошли в Ремонт.бг"
                        , """
                                Добре дошли!
                                Вече може да ползвате всички наши услуги!
                                Поздрави,
                                Екипът на ремонт.бг!"""
                        ,"remontprojectbg@gmail.com");;

        return modelMapper.map(savedUser,UserServiceModel.class);


    }

    @Override
    public boolean existByUsername(String username) {
        return this.userRepository.existsByUsername(username);
    }

    @Override
    public boolean existByEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }


    @Override
    public UserServiceModel findUserById(String id) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new UserWithIdNotExists("User with this id does not exist!"));
        return this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public List<UserServiceModel> findAllUsers() {
        return userRepository.findAll().stream()
                .map(u -> modelMapper.map(u, UserServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void updateProfile(UserServiceModel userServiceModel, String username) {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with NOT FOUND  username: %s", username)));
        user.setUsername(username);
        user.setFirstName(userServiceModel.getFirstName());
        user.setLastName(userServiceModel.getLastName());
        user.setEmail(userServiceModel.getEmail());
        user.setPhoneNumber(userServiceModel.getPhoneNumber());
        user.setCity(userServiceModel.getCity());
        user.setImage(userServiceModel.getImage());
        System.out.println();
        userRepository.saveAndFlush(user);


        modelMapper.map(user, UserServiceModel.class);


    }


    @Override
    public void setUserRole(String id, String role) {

        User user = userRepository.findById(id).orElseThrow(() -> new UserWithIdNotExists("User with this id does not exist!"));
        UserServiceModel userServiceModel = modelMapper.map(user, UserServiceModel.class);
        userServiceModel.getAuthorities().clear();
        switch (role) {
            case "user" -> userServiceModel.getAuthorities().add(roleService.findByAuthority("ROLE_USER"));
            case "moderator" -> {
                userServiceModel.getAuthorities().add(roleService.findByAuthority("ROLE_USER"));
                userServiceModel.getAuthorities().add(roleService.findByAuthority("ROLE_MODERATOR"));
            }
            case "admin" -> {
                userServiceModel.getAuthorities().add(roleService.findByAuthority("ROLE_USER"));
                userServiceModel.getAuthorities().add(roleService.findByAuthority("ROLE_MODERATOR"));
                userServiceModel.getAuthorities().add(roleService.findByAuthority("ROLE_ADMIN"));
            }
        }
        userRepository.saveAndFlush(modelMapper.map(userServiceModel, User.class));
        user.setPassword(userServiceModel.getPassword() != null ? encoder.encode(userServiceModel.getPassword()) :
                user.getPassword());


    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with NOT FOUND  username: %s", username)));
    }

    @Override
    public UserServiceModel changePassword(UserServiceModel userServiceModel, String oldPassword, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username not found %s", userServiceModel.getUsername())));

        if (!encoder.matches(oldPassword, user.getPassword())) {
            throw new UserOldPasswordNotCorrectException("INCORRECT_PASSWORD");
        }

        user.setPassword(userServiceModel.getPassword() != null ? encoder.encode(userServiceModel.getPassword()) :
                user.getPassword());
        userRepository.saveAndFlush(user);
        return modelMapper.map(user, UserServiceModel.class);
    }


}
