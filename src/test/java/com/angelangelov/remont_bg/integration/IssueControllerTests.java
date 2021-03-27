package com.angelangelov.remont_bg.integration;

import com.angelangelov.remont_bg.RemontBgApplication;
import com.angelangelov.remont_bg.model.entities.Issue;
import com.angelangelov.remont_bg.model.entities.Role;
import com.angelangelov.remont_bg.model.entities.User;
import com.angelangelov.remont_bg.repository.IssueRepository;
import com.angelangelov.remont_bg.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@ContextConfiguration(classes = RemontBgApplication.class)
@RunWith(SpringRunner.class)
public class IssueControllerTests {

    private String testIssueId;
    private String userId;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IssueRepository issueRepository;

    private static final String ISSUE_CONTROLLER_PREFIX = "/issue";
    User testUser;

    @BeforeEach
    public void setUp() {
        this.init();

    }

    @Test
    @WithMockUser(username = "pesho", roles = {"USER, ADMIN"})
    public void testAddIssueShouldReturnValid() throws Exception {
        InputStream is = new FileInputStream("src/test/java/resources/img/testImg.png");
        System.out.println();
        MockMultipartFile image = new MockMultipartFile("problemImgUrl", is);

        mockMvc.perform(MockMvcRequestBuilders
                .multipart("/issue/submit")
                .file(image)
                .param("problemName", "TestName")
                .param("problemDescription", "problemDescription")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:success"));

        //TODO : QUESTION: не успява да ми намери юзъра(Principal), който ползвам в контролера


    }


    @Test
    @WithMockUser(username = "test", roles = {"USER, ADMIN"})
    public void testGetToolCategoriesShouldWork() throws Exception {
        String userId = this.userId;

        mockMvc.perform(MockMvcRequestBuilders.get("/issue/submit"))
                .andExpect(status().isOk())
                .andExpect(view().name("/issue/issue-page"))
                .andExpect(model().attributeExists("issueAddBindingModel"));
    }

    private void init() {



        User userEntity = new User();
        userEntity.setUsername("pesho");
        userEntity.setPassword("xyz");
        userEntity.setCreatedDate(LocalDateTime.now());

        userEntity = userRepository.save(userEntity);


    }
}
