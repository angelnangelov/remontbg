package com.angelangelov.remont_bg.integration;

import com.angelangelov.remont_bg.RemontBgApplication;
import com.angelangelov.remont_bg.model.entities.ToolCategory;
import com.angelangelov.remont_bg.model.entities.ToolOffer;
import com.angelangelov.remont_bg.model.entities.User;
import com.angelangelov.remont_bg.model.entities.enums.Region;
import com.angelangelov.remont_bg.model.entities.enums.ToolsCategoryName;
import com.angelangelov.remont_bg.model.services.ToolOfferServiceModel;
import com.angelangelov.remont_bg.model.services.UserServiceModel;
import com.angelangelov.remont_bg.repository.ToolOfferRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@AutoConfigureMockMvc
@SpringBootTest
@ContextConfiguration(classes = RemontBgApplication.class)
@RunWith(SpringRunner.class)
public class ToolControllerTests {

    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser(username = "test",roles = {"USER, ADMIN"})
    public void testGetToolCategoriesShouldWork() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/tool/categories"))
                .andExpect(status().isOk())
                .andExpect(view().name("tools/all-tools-categories"))
                .andExpect(model().attributeExists("allTools"));
    }
    @Test
    @WithMockUser(username = "test",roles = {"USER, ADMIN"})
    public void testAddToolsShouldWork() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/tool/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("tools/add-tool"))
                .andExpect(model().attributeExists("toolOfferAddBindingModel"));
    }
    @Test
    @WithMockUser(username = "test",roles = {"USER, ADMIN"})
    public void allToolsByCategoryShouldWork() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/tool/category/808b070e-c3ae-4a87-abda-c4695949552b"))
                .andExpect(status().isOk())
                .andExpect(view().name("tools/all-tools"))
                .andExpect(model().attributeExists("tools","toolName"));
    }
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
