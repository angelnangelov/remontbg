package com.angelangelov.remont_bg.integration;

import com.angelangelov.remont_bg.RemontBgApplication;
import com.angelangelov.remont_bg.repository.LogRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@SpringBootTest
@ContextConfiguration(classes = RemontBgApplication.class)
@RunWith(SpringRunner.class)
public class HomeControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    LogRepository logRepository;


    @Test
    public void testIndex_WhenUserIsAnonymous_ShouldReturnIndex() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }
    @Test
    public void testAbout_WhenUserIsAnonymous_ShouldReturnIndex() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/about"))
                .andExpect(status().isOk())
                .andExpect(view().name("about-us"));
    }

    @Test
    @WithMockUser(username = "test",roles = {"USER, ADMIN"})
    public void testIndex_WhenUserIsLogged_ShouldReturnHome() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));

        //TODO : CHECK LECTION
    }


}
