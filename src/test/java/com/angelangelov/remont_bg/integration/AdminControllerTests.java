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
public class AdminControllerTests {

    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser(username = "test", roles = {"ADMIN"})
    public void testAdminActions_WhenAdmin_ShouldOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/actions"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/admin-page"));
    }

    @Test
    @WithMockUser(username = "test", roles = {"ADMIN"})
    public void testAdminUnapprovedTools_WhenAdmin_ShouldOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/tools"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/all-tools-approve"))
                .andExpect(model().attributeExists("unapprovedTools"));

    }

    @Test
    @WithMockUser(username = "test", roles = {"ADMIN"})
    public void testAdminUnapprovedOffers_WhenAdmin_ShouldOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/offers"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/all-offers-approve"))
                .andExpect(model().attributeExists("unapprovedOffers"));

    }

    @Test
    @WithMockUser(username = "test", roles = {"ADMIN"})
    public void testAdminUserActions_WhenAdmin_ShouldOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/all-users"))
                .andExpect(model().attributeExists("users"));

    }

    @Test
    @WithMockUser(username = "test", roles = {"USER"})
    public void testAdminActions_WhenUser_ShouldThrow() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/actions"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "test", roles = {"USER"})
    public void testAdminUnapprovedTools_WhenUserShould_Throw() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/tools"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "test", roles = {"USER"})
    public void testAdminUnapprovedOffers_WhenUser_ShouldThrow() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/offers"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "test", roles = {"USER"})
    public void testAdminUserActions_WhenUser_ShouldThrow() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/users"))
                .andExpect(status().is4xxClientError());
    }
}
