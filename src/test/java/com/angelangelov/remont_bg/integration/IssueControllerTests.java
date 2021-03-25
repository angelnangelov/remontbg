package com.angelangelov.remont_bg.integration;

import com.angelangelov.remont_bg.RemontBgApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@ContextConfiguration(classes = RemontBgApplication.class)
@RunWith(SpringRunner.class)
public class IssueControllerTests {
    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser(username = "test",roles = {"USER, ADMIN"})
    public void testGetToolCategoriesShouldWork() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/issue/submit"))
                .andExpect(status().isOk())
                .andExpect(view().name("/issue/issue-page"))
                .andExpect(model().attributeExists("issueAddBindingModel"));
    }
}
