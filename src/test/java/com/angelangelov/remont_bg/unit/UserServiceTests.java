package com.angelangelov.remont_bg.unit;

import com.angelangelov.remont_bg.model.entities.User;
import com.angelangelov.remont_bg.model.services.RoleServiceModel;
import com.angelangelov.remont_bg.model.services.UserServiceModel;
import com.angelangelov.remont_bg.repository.UserRepository;
import com.angelangelov.remont_bg.service.RoleService;
import com.angelangelov.remont_bg.service.impl.UserServiceImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserServiceTests {
    @InjectMocks
    UserServiceImpl userService;
    @Mock
    UserRepository userRepository;
    @Mock
    RoleService roleService;

    @Mock
    ModelMapper modelMapper;
    @Mock
    BCryptPasswordEncoder encoder;


    User user;
    UserServiceModel userServiceModel;

    @Before
    public void initTests() {
        user = new User();
        userServiceModel = new UserServiceModel();
        userServiceModel.setUsername("name");
        userServiceModel.setEmail("email");
        userServiceModel.setPassword("1");
        userServiceModel.setCity("city");
        userServiceModel.setFirstName("firstName");
        userServiceModel.setAuthorities(Set.of(new RoleServiceModel()));
        user.setUsername("name");

    }

    @Test
    public void findUserByUsername_WhenUserExist_ShouldWork() {


//        User userTest = new User();
//        userTest.setUsername("test");
//        Mockito.when(userRepository.findByUsername("test"))
//                .thenReturn(Optional.of(userTest));
//        Mockito.when(modelMapper.map(userTest, UserServiceModel.class))
//                .thenReturn(model);
//        UserServiceModel result = userService.findUserByUsername("test");
//        assertEquals(model, result);

        //TODO: question

    }


    @Test
    public void loadUserByUsername_WhenUserExist_ShouldWork() {
        Mockito.when(userRepository.findByUsername("name"))
                .thenReturn(Optional.of(user));
        User userResult = (User) userService.loadUserByUsername("name");
        assertEquals(user, userResult);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsername_WhenNotUserExist_ShouldThrow() {
        Mockito.when(userRepository.findByUsername("name"))
                .thenThrow(UsernameNotFoundException.class);
        userService.loadUserByUsername("name");
    }


    @Test(expected = UsernameNotFoundException.class)
    public void findByUsername_WhenNotUserExist_ShouldThrow() {
        Mockito.when(userRepository.findByUsername("name"))
                .thenThrow(UsernameNotFoundException.class);
        userService.loadUserByUsername("name");
    }



    @Test
    public void findAllUsers_WhenUsersExist_ShouldWork(){
        List<User> users = new ArrayList<>();
        users.add(user);
        System.out.println();
        Mockito.when(userRepository.findAll())
                .thenReturn(users);
        int result = userService.findAllUsers().size();
        System.out.println();
        assertEquals(users.size(), result);
    }
}


