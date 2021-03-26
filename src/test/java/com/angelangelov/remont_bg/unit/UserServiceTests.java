package com.angelangelov.remont_bg.unit;

import com.angelangelov.remont_bg.error.user.UserOldPasswordNotCorrectException;
import com.angelangelov.remont_bg.error.user.UserWithIdNotExists;
import com.angelangelov.remont_bg.error.user.UserWithUsernameNotExists;
import com.angelangelov.remont_bg.model.entities.Role;
import com.angelangelov.remont_bg.model.entities.User;
import com.angelangelov.remont_bg.model.services.RoleServiceModel;
import com.angelangelov.remont_bg.model.services.UserServiceModel;
import com.angelangelov.remont_bg.repository.UserRepository;
import com.angelangelov.remont_bg.service.EmailService;
import com.angelangelov.remont_bg.service.RoleService;
import com.angelangelov.remont_bg.service.impl.UserServiceImpl;

import org.junit.Assert;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceTests {

    @Mock
    UserRepository mockUserRepository;
    @Mock
    RoleService mockRoleService;
    @Mock
    BCryptPasswordEncoder mockEncoder;
    @Mock
    EmailService mockEmailService;
    @Mock
    PasswordEncoder passwordEncoder;

    UserServiceImpl serviceToTest;
    User testUser;

    @Before
    public void initTests() {
        serviceToTest = new UserServiceImpl(
                new ModelMapper(),
                mockUserRepository,
                mockRoleService,
                mockEmailService,
                mockEncoder
        );

    }


    @Test
    public void findUserByUsername_WhenUserExist_ShouldWork() {
        User testUser = new User();
        testUser.setUsername("name");
        testUser.setEmail("name@example.com");
        Role sampleRole = new Role();
        sampleRole.setAuthority("TEST_AUTHORITY");
        testUser.setAuthorities(Set.of(sampleRole));
        Mockito.when(mockUserRepository.findUserByUsername("name"))
                .thenReturn(Optional.of(testUser));
        UserServiceModel result = serviceToTest.findUserByUsername("name");
        Assert.assertEquals(result.getUsername(), testUser.getUsername());
        Assert.assertEquals(result.getEmail(), testUser.getEmail());
        Assert.assertEquals(1, result.getAuthorities().size());
    }

    @Test
    public void loadUserByUsername_WhenUserExist_ShouldWork() {
        User testUser = new User();
        testUser.setUsername("name");
        testUser.setEmail("name@example.com");
        Mockito.when(mockUserRepository.findByUsername("name"))
                .thenReturn(Optional.of(testUser));
        User userResult = (User) serviceToTest.loadUserByUsername("name");
        assertEquals(testUser, userResult);
    }

    @Test
    public void existByUsername_WhenUserDoesNotExists_ShouldReturnFalse() {

        boolean existByNameResult = serviceToTest.existByUsername("name");
        assertFalse(existByNameResult);
    }


    @Test
    public void existByUsername_WhenUserExists_ShouldReturnTrue() {
        User testUser = new User();
        testUser.setUsername("name");
        testUser.setEmail("name@example.com");
        Mockito.when(mockUserRepository.existsByUsername("name"))
                .thenReturn(true);
        boolean existByNameResult = serviceToTest.existByUsername("name");
        assertTrue(existByNameResult);

    }

    @Test
    public void existByEmail_WhenEmailDoesNotExists_ShouldReturnFalse() {

        boolean existByEmail = serviceToTest.existByEmail("name");
        assertFalse(existByEmail);
    }


    @Test
    public void existByEmail_WhenEmailExists_ShouldReturnTrue() {
        User testUser = new User();
        testUser.setUsername("name");
        testUser.setEmail("name@example.com");
        Mockito.when(mockUserRepository.existsByEmail("name@example.com"))
                .thenReturn(true);
        boolean existByEmail = serviceToTest.existByEmail("name@example.com");
        assertTrue(existByEmail);

    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsername_WhenNotUserExist_ShouldThrow() {
        Mockito.when(mockUserRepository.findByUsername("name"))
                .thenThrow(UsernameNotFoundException.class);
        serviceToTest.loadUserByUsername("name");
    }

    @Test
    public void findUserById_WhenIExist_ShouldThrow() {
        User testUser = new User();
        testUser.setId("testId");
        testUser.setUsername("name");
        testUser.setEmail("name@example.com");
        Role sampleRole = new Role();
        sampleRole.setAuthority("TEST_AUTHORITY");
        testUser.setAuthorities(Set.of(sampleRole));
        Mockito.when(mockUserRepository.findById("testId"))
                .thenReturn(Optional.of(testUser));
        UserServiceModel result = serviceToTest.findUserById("testId");
        Assert.assertEquals(result.getUsername(), testUser.getUsername());
        Assert.assertEquals(result.getEmail(), testUser.getEmail());
        Assert.assertEquals(1, result.getAuthorities().size());
    }

    @Test(expected = UserWithIdNotExists.class)
    public void findUserById_WhenIdNotExist_ShouldThrow() {
        Mockito.when(serviceToTest.findUserById("testId")).thenThrow(UserWithIdNotExists.class);
        serviceToTest.findUserById("testId");
    }


    @Test(expected = UserWithUsernameNotExists.class)
    public void findUserByUsername_WhenIdNotExist_ShouldThrow() {
        Mockito.when(serviceToTest.findUserByUsername("testName")).thenThrow(UserWithUsernameNotExists.class);
        serviceToTest.findUserByUsername("testName");
    }


    @Test(expected = UsernameNotFoundException.class)
    public void findByUsername_WhenNotUserExist_ShouldThrow() {
        Mockito.when(mockUserRepository.findByUsername("name"))
                .thenThrow(UsernameNotFoundException.class);
        serviceToTest.loadUserByUsername("name");
    }


    @Test
    public void findAllUsers_WhenUsersExist_ShouldWork() {
        List<User> users = new ArrayList<>();
        User testUser = new User();
        testUser.setUsername("name");
        testUser.setEmail("name@example.com");
        users.add(testUser);
        System.out.println();
        Mockito.when(mockUserRepository.findAll())
                .thenReturn(users);
        int result = serviceToTest.findAllUsers().size();
        System.out.println();
        assertEquals(users.size(), result);
    }
}
