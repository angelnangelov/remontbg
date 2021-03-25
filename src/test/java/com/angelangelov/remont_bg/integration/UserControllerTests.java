package com.angelangelov.remont_bg.integration;

import com.angelangelov.remont_bg.RemontBgApplication;

import com.angelangelov.remont_bg.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@ContextConfiguration(classes = RemontBgApplication.class)
@RunWith(SpringRunner.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    protected UserRepository userRepository;
    @MockBean
    protected ModelMapper modelMapper;


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


//    @Test
//    @WithMockUser(username = "test",roles = {"USER, ADMIN"})
//    public void testUserProfile_ShouldWork() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/user/profile"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("/user/user-profile"))
//                .andExpect(model().attributeExists("userEditBindingModel","username","profilePic"));
//    }
}
