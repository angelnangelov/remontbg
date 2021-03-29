package com.angelangelov.remont_bg.integration;

import com.angelangelov.remont_bg.RemontBgApplication;

import com.angelangelov.remont_bg.model.entities.User;
import com.angelangelov.remont_bg.model.services.UserServiceModel;
import com.angelangelov.remont_bg.repository.UserRepository;
import com.angelangelov.remont_bg.service.CloudinaryService;
import com.angelangelov.remont_bg.service.UserService;
import com.angelangelov.remont_bg.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    protected UserRepository userRepository;


    @MockBean
    private UserService userService;
    @MockBean
    private CloudinaryService cloudinaryService;
    @MockBean
    private PasswordEncoder encoder;



    @Before
    public void setup() {
      this.init();


    }

    @Test
    public void testGetLoginWhenAnonymousShouldWork() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/login"));
    }

    @Test
    public void testGetRegisterWhenAnonymousShouldWork() throws Exception {
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


    @Test
    @WithMockUser(username = "pesho", roles = {"USER, ADMIN"})
    public void testPostUpdateProfileShouldWorkWhenEverythingIsCorrect() throws Exception {
        InputStream is = new FileInputStream("src/test/java/com/resources/img/testImg.png");
        MockMultipartFile image = new MockMultipartFile("image", is);
        mockMvc.perform(MockMvcRequestBuilders
                .multipart("/user/profile")
                .file(image)
                .param("firstName", "test")
                .param("lastName", "testLast")
                .param("email", "test@test.bg")
                .param("city","Plovdiv")
                .param("phoneNumber","12561212")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                        .andExpect(view().name("redirect:profile"));
    }



    @Test
    @WithMockUser(username = "pesho", roles = {"USER, ADMIN"})
    public void testPostUpdateProfileShouldNotPostWhenIncorrectFields() throws Exception {
        InputStream is = new FileInputStream("src/test/java/com/resources/img/testImg.png");
        MockMultipartFile image = new MockMultipartFile("image", is);
        mockMvc.perform(MockMvcRequestBuilders
                .multipart("/user/profile")
                .file(image)
                .param("firstName", "p")
                .param("lastName", "testLast")
                .param("email", "test@test.bg")
                .param("city","Plovdiv")
                .param("phoneNumber","0")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:profile"));
    }
    @Test
    @WithMockUser(username = "pesho", roles = {"USER, ADMIN"})
    public void tesGetPasswordChange_ShouldWork() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/user/passwordChange")
)
                .andExpect(status().isOk())
                .andExpect(view().name("passwordChange"))
                .andExpect(model().attributeExists("userPasswordChangeBindingModel"));
    }


    private void init() {
        User testUser = new User();
        testUser.setUsername("pesho");
        testUser.setFirstName("pesho");
        testUser.setLastName("pesho");
        testUser.setPassword("123456");
        testUser.setEmail("1234@asdadas");
        testUser.setImage("https://res.cloudinary.com/dtns2qohp/image/upload/v1615538666/userb_gk7xzm.png");
        userRepository.save(testUser);
    }
}