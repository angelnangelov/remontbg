package com.angelangelov.remont_bg.integration;


import com.angelangelov.remont_bg.model.entities.User;
import com.angelangelov.remont_bg.repository.IssueRepository;
import com.angelangelov.remont_bg.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class IssueControllerTests {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IssueRepository issueRepository;



    @BeforeEach
    public void setup() {

        this.init();
    }



    @Test
    @WithMockUser(username = "user", roles = {"USER, ADMIN"})
    public void testAddIssueShouldReturnValid() throws Exception {
        InputStream is = new FileInputStream("src/test/java/com/resources/img/testImg.png");
        System.out.println();
        MockMultipartFile image = new MockMultipartFile("problemImgUrl", is);

        mockMvc.perform(MockMvcRequestBuilders
                .multipart("/issue/submit")
                .file(image)
                .param("problemName", "TestName")
                .param("problemDescription", "problemDescription")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("/issue/success-issue-page"));

    }

    @Test
    @WithMockUser(username = "user", roles = {"USER, ADMIN"})
    public void testAddIssueShouldRedirectWhenFieldsNotCorrect() throws Exception {
        InputStream is = new FileInputStream("src/test/java/com/resources/img/testImg.png");
        System.out.println();
        MockMultipartFile image = new MockMultipartFile("problemImgUrl", is);

        mockMvc.perform(MockMvcRequestBuilders
                .multipart("/issue/submit")
                .file(image)
                .param("s", "TestName")
                .param("problemDescription", "problemDescription")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:submit"));

    }



    @Test
    @WithMockUser(username = "user", roles = {"USER, ADMIN"})
    public void testGetIssuePageShouldWork() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/issue/submit"))
                .andExpect(status().isOk())
                .andExpect(view().name("/issue/issue-page"))
                .andExpect(model().attributeExists("issueAddBindingModel"));
    }
    private void init() {
        User userEntity = new User();
        userEntity.setUsername("user");
        userEntity.setFirstName("pesho");
        userEntity.setLastName("pesho");
        userEntity.setPassword("xyz");
        userEntity.setPassword("1234");
        userEntity.setEmail("11234@asdadas");
        userRepository.save(userEntity);
    }
}
