package com.angelangelov.remont_bg.service.impl;

import com.angelangelov.remont_bg.model.entities.Role;
import com.angelangelov.remont_bg.model.services.RoleServiceModel;
import com.angelangelov.remont_bg.repository.RoleRepository;
import com.angelangelov.remont_bg.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;


@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
@Autowired
    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public void seedRoles() {
        if (roleRepository.count() == 0) {
            roleRepository.save(new Role("ROLE_USER"));
            roleRepository.save(new Role("ROLE_ADMIN"));
            roleRepository.save(new Role("ROLE_MODERATOR"));
            roleRepository.save(new Role("ROLE_REDACTOR"));
        }
    }

    @Override
    public Set<RoleServiceModel> findAll() {
        return roleRepository.findAll().stream()
                .map(r -> modelMapper.map(r, RoleServiceModel.class))
                .collect(Collectors.toSet());
    }

    @Override
    public RoleServiceModel findByAuthority(String authority) {
        return modelMapper.map(roleRepository.findByAuthority(authority), RoleServiceModel.class);
    }
}
