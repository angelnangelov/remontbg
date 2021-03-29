package com.angelangelov.remont_bg.integration;

import com.angelangelov.remont_bg.RemontBgApplication;
import com.angelangelov.remont_bg.model.entities.*;
import com.angelangelov.remont_bg.model.entities.enums.Region;
import com.angelangelov.remont_bg.model.entities.enums.ServiceOfferNames;
import com.angelangelov.remont_bg.model.entities.enums.ToolsCategoryName;
import com.angelangelov.remont_bg.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;




@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ToolControllerTests {


    private String toolId;
    private String toolCategoryId;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ToolOfferRepository offerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ToolCategoryRepository toolCategoryRepository;


    private static final String TOOL_CONTROLLER_PREFIX = "/tool";


    @BeforeEach
    public void setup() {
        this.init();
    }

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
    public void testGetAddToolsShouldWork() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/tool/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("tools/add-tool"))
                .andExpect(model().attributeExists("toolOfferAddBindingModel"));
    }
    @Test
    @WithMockUser(username = "pesho", roles = {"USER, ADMIN"})
    public void testPostAddToolShouldWorkWhenEverythingCorrect() throws Exception {
        InputStream is = new FileInputStream("src/test/java/com/resources/img/testImg.png");
        System.out.println();
        MockMultipartFile image = new MockMultipartFile("image", is);
        mockMvc.perform(MockMvcRequestBuilders
                .multipart("/tool/add")
                .file(image)
                .param("name", "testName")
                .param("brand","testBrand")
                .param("model","testModel")
                .param("price", "123")
                .param("deposit", "124")
                .param("category", "Почистващи")
                .param("startsOn", "2021-03-29")
                .param("endsOn", "2022-03-25")
                .param("region", "Пловдив")
                .param("ownerPhoneNumber", "08812331231")
                .param("description", "description")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("success-add-page"));
    }

    @Test
    @WithMockUser(username = "pesho", roles = {"USER, ADMIN"})
    public void testGetOffersApprovedAndActiveInCategoryShouldWork() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                TOOL_CONTROLLER_PREFIX + "/category/{id}", toolCategoryId
        )).
                andExpect(status().isOk()).
                andExpect(view().name("tools/all-tools")).
                andExpect(model().attributeExists("toolName", "tools"));
    }

    @Test
    @WithMockUser(username = "pesho", roles = {"USER, ADMIN"})
    public void testGetSingleOfferInCategoryShouldWork() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                TOOL_CONTROLLER_PREFIX + "/single-tool/{id}", toolId
        )).
                andExpect(status().isOk()).
                andExpect(view().name("tools/tool-product-view")).
                andExpect(model().attributeExists("toolOffer"));
    }
    @Test
    @WithMockUser(username = "pesho", roles = {"USER, ADMIN"})
    public void testGetAllUserOffers_ShouldWork() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get( "/tool/userTools"))
                .andExpect(status().isOk())
                .andExpect(view().name("/user/all-user-tools"))
                .andExpect(model().attributeExists("allUserTools"));
    }

    @Test
    @WithMockUser(username = "pesho", roles = {"USER, ADMIN"})
    public void testUserCanDeleteItsOwnTool() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                TOOL_CONTROLLER_PREFIX + "/user/deleteTool/")
                .param("id", toolId)
        ).
                andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/tool/userTools"));
    }

    @Test
    @WithMockUser(username = "pesho", roles = {"USER, ADMIN"})
    public void testGetUpdateToolOfferPageShouldWork() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                TOOL_CONTROLLER_PREFIX + "/update-tool/{id}", toolId
        )).
                andExpect(status().isOk()).
                andExpect(view().name("tools/update-tool")).
                andExpect(model().attributeExists("toolEditBindingModel"));
    }

    @Test
    @WithMockUser(username = "pesho", roles = {"USER, ADMIN"})
    public void testPostUpdateToolOfferWhenCorrectFieldsShouldWork() throws Exception {
        InputStream is = new FileInputStream("src/test/java/com/resources/img/testImg.png");
        System.out.println();
        MockMultipartFile image = new MockMultipartFile("image", is);
        mockMvc.perform(MockMvcRequestBuilders
                .multipart(TOOL_CONTROLLER_PREFIX + "/update-tool/{id}", toolId)
                .file(image)
                .param("name", "TestName")
                .param("price", "123")
                .param("power", "123242")
                .param("brand", "newBrand")
                .param("deposit", "123")
                .param("model", "newModel")
                .param("category", "Водопровдчик")
                .param("startsOn", "2021-03-29")
                .param("endsOn", "2022-03-25")
                .param("region", "Пловдив")
                .param("ownerPhoneNumber", "08812331231")
                .param("description", "description")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("tools/success-updateTool-page"));
    }



    private void init () {
        User testUser = new User();
        testUser.setUsername("pesho");
        testUser.setFirstName("pesho");
        testUser.setLastName("pesho");
        testUser.setPassword("xyz");
        testUser.setPassword("1234");
        testUser.setEmail("1234@asdadas");
        userRepository.save(testUser);

        ToolOffer toolOffer = new ToolOffer();
        toolOffer.setName("testOfferName");
        toolOffer.setDescription("testOfferDescription");
        toolOffer.setImage("https://res.cloudinary.com/dtns2qohp/image/upload/v1615288082/depositphotos_318221368-stock-illustration-missing-picture-page-for-website_hppqti.jpg");
        toolOffer.setApproved(true);
        toolOffer.setActive(true);
        toolOffer.setStartsOn(LocalDate.MAX);
        toolOffer.setEndsOn(LocalDate.MAX);
        toolOffer.setPrice(BigDecimal.valueOf(12));
        toolOffer.setPower("12W");
        toolOffer.setBrand("STIHL");
        toolOffer.setModel("RES211MA");
        toolOffer.setDeposit(BigDecimal.valueOf(12));
        toolOffer.setOwnerPhoneNumber("+035911231");
        toolOffer.setRegion(Region.Бургас);
        toolOffer.setUser(testUser);
        ToolCategory testOfferCat = toolCategoryRepository.findByName(ToolsCategoryName.Почистващи);
        toolOffer.setToolCategory(testOfferCat);
        offerRepository.save(toolOffer);
        toolId = toolOffer.getId();
        toolCategoryId = testOfferCat.getId();


    }

}
