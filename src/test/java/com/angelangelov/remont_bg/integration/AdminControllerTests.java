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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)

public class AdminControllerTests {

    @Autowired
    private MockMvc mockMvc;
    private String toolId;
    private String toolCategoryId;
    private String offerId;
    private String categoryId;


    @Autowired
    private ToolOfferRepository toolOfferRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ToolCategoryRepository toolCategoryRepository;


    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private OfferCategoryRepository offerCategoryRepository;
    @Autowired
    private CommentRepository commentRepository;

    @BeforeEach
    public void setup() {
        this.init();
    }

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

    //

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testApproveOfferShouldWorkWhenAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                "/admin/offers/approveOffer/")
                .param("id", offerId)
        ).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/admin/offers"));

    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testApproveToolShouldWorkWhenAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                "/admin/tools/approveTool/")
                .param("id", toolId)
        ).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/admin/tools"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testDeleteOfferShouldWorkWhenAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                "/admin/offers/deleteOffer/")
                .param("id", offerId)
        ).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/admin/offers"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testDeleteToolShouldWorkWhenAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                "/admin/tools/deleteTool/")
                .param("id", toolId)
        ).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/admin/tools"));
    }



    private void init() {
        User adminTest = new User();
        adminTest.setUsername("admin");
        adminTest.setFirstName("admin");
        adminTest.setLastName("admin");
        adminTest.setPassword("admin");
        adminTest.setPassword("admin");
        adminTest.setEmail("admin@admin");
        userRepository.save(adminTest);
        User testUser = new User();
        testUser.setUsername("pesho");
        testUser.setFirstName("pesho");
        testUser.setLastName("pesho");
        testUser.setPassword("xyz");
        testUser.setPassword("1234");
        testUser.setEmail("test@test");
        userRepository.save(testUser);


        Comment testComent = new Comment();
        LogEntity testLogs = new LogEntity();
        Offer testOffer = new Offer();
        testOffer.setName("testOfferName");
        testOffer.setDescription("testOfferDescription");
        testOffer.setImage("https://res.cloudinary.com/dtns2qohp/image/upload/v1615288082/depositphotos_318221368-stock-illustration-missing-picture-page-for-website_hppqti.jpg");
        testOffer.setApproved(false);
        testOffer.setActive(true);
        testOffer.setStartsOn(LocalDate.MAX);
        testOffer.setEndsOn(LocalDate.MAX);
        testOffer.setPrice(BigDecimal.valueOf(12));
        testOffer.setOwnerPhoneNumber("+035911231");
        testOffer.setRegion(Region.Бургас);
        testOffer.setComments(List.of(testComent));
        testOffer.setLogs(Set.of(testLogs));
        testOffer.setUser(testUser);
        OfferCategory testOfferCat = offerCategoryRepository.findByName(ServiceOfferNames.Водопровдчик);
        testOffer.setCategory(testOfferCat);
        offerRepository.save(testOffer);
        offerId = testOffer.getId();
        categoryId = testOfferCat.getId();
        ToolOffer toolOffer = new ToolOffer();
        toolOffer.setName("testOfferName");
        toolOffer.setDescription("testOfferDescription");
        toolOffer.setImage("https://res.cloudinary.com/dtns2qohp/image/upload/v1615288082/depositphotos_318221368-stock-illustration-missing-picture-page-for-website_hppqti.jpg");
        toolOffer.setApproved(false);
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
        ToolCategory toolOfferCat = toolCategoryRepository.findByName(ToolsCategoryName.Почистващи);
        toolOffer.setToolCategory(toolOfferCat);
        toolOfferRepository.save(toolOffer);
        toolId = toolOffer.getId();
        toolCategoryId = toolOfferCat.getId();
    }
}
