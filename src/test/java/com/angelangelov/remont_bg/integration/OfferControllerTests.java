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
public class OfferControllerTests {

    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser(username = "test",roles = {"USER, ADMIN"})
    public void testGetOfferCategories_ShouldWork() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/offer/categories"))
                .andExpect(status().isOk())
                .andExpect(view().name("offers/all-offer-categories"))
                .andExpect(model().attributeExists("allCategories"));
    }
    @Test
    @WithMockUser(username = "test",roles = {"USER, ADMIN"})
    public void testAddOffer_ShouldWork() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/offer/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("offers/add-offer"))
                .andExpect(model().attributeExists("offerAddBindingModel"));
    }

//    @Test
//    @WithMockUser(username = "test",roles = {"USER, ADMIN"})
//    public void testAllUserOffers_ShouldWork() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/offer/userOffers"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("/user/all-user-offers"))
//                .andExpect(model().attributeExists("allUserOffers"));
//    }

//    @Test
//    @WithMockUser(username = "test",roles = {"USER, ADMIN"})
//    public void allUserToolShouldWork() throws Exception {
//        Mockito.when(toolOfferRepository.findById("1")).thenReturn(Optional.of(toolOffer));
//        mockMvc.perform(MockMvcRequestBuilders.get("/tool/category/"+toolOffer.getId()))
//                .andExpect(status().isOk())
//                .andExpect(view().name("tools/all-tools"))
//                .andExpect(model().attributeExists("tools","toolName"));
//    }

}
