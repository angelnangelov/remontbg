package com.angelangelov.remont_bg.integration;

import com.angelangelov.remont_bg.RemontBgApplication;

import com.angelangelov.remont_bg.repository.UserRepository;
import com.angelangelov.remont_bg.service.CloudinaryService;
import com.angelangelov.remont_bg.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@ContextConfiguration(classes = RemontBgApplication.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)


@RunWith(SpringRunner.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    protected UserRepository userRepository;
    @MockBean
    protected ModelMapper modelMapper;
    @MockBean
    private  UserService userService;
    @MockBean
    private  CloudinaryService cloudinaryService;
    @MockBean
    private  PasswordEncoder encoder;



    @Test
    public void testLoginWhenAnonymousShouldWork() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/login"));
    }

    @Test
    public void testRegisterWhenAnonymousShouldWork() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/register"))
                .andExpect(model().attributeExists("userRegisterBindingModel"));
    }
    @Test
    public void testRegisterUserWithValidFieldsShouldWork() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/user/register")
                .param("username", "test")
                .param("password", "testtestbg")
                .param("confirmPassword", "testtestbg")
                .param("firstName", "test")
                .param("lastName", "testLast")
                .param("email", "test@test.bg")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:login"));
    }
    @Test
    public void testRegisterUserWithValidFieldsShouldRedirectAgainOnRegister() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/user/register")
                .param("username", "test")
                .param("password", "testtestbg")
                .param("firstName", "test")
                .param("email", "test@test.bg")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:register"));
    }
//    @Test
//    @WithMockUser(username = "test",roles = {"USER, ADMIN"})
//    public void testUserProfile_ShouldWork() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/user/profile"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("/user/user-profile"))
//                .andExpect(model().attributeExists("userEditBindingModel","username","profilePic"));
//    }
}
