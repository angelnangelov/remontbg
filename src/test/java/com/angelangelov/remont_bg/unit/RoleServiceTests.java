package com.angelangelov.remont_bg.unit;

import com.angelangelov.remont_bg.model.entities.Role;
import com.angelangelov.remont_bg.model.services.RoleServiceModel;
import com.angelangelov.remont_bg.repository.RoleRepository;
import com.angelangelov.remont_bg.service.impl.RoleServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class RoleServiceTests {

    @InjectMocks
    RoleServiceImpl service;
    @Mock
    RoleRepository roleRepository;
    @Mock
    ModelMapper modelMapper;

    @Test
    public void seedRoles_WhenNoRoles_ShouldSeed(){
        Role role = new Role("test");
        Mockito.when(roleRepository.count())
                .thenReturn(0L);
        service.seedRoles();
        assertEquals(0, roleRepository.findAll().size());
    }

    @Test
    public void findAll_WhenOneROle_ShouldReturn1(){
        Role role = new Role("test");
        List<Role> roles = List.of(role);
       Mockito.when(roleRepository.findAll())
               .thenReturn(roles);
       int result = service.findAll().size();
       assertEquals(1, result);

    }

    @Test
    public void findByAuthority_WhenOneRoleExist_ShouldWork(){
        RoleServiceModel roleService = new RoleServiceModel();
        Role role = new Role("test");
        Mockito.when(roleRepository.findByAuthority("test"))
                .thenReturn(role);
        Mockito.when(modelMapper.map(role, RoleServiceModel.class))
                .thenReturn(roleService);
        RoleServiceModel result = service.findByAuthority("test");
        assertEquals(roleService, result);

    }
}
