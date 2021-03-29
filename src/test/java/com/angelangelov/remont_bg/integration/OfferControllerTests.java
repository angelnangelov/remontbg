package com.angelangelov.remont_bg.integration;


import com.angelangelov.remont_bg.model.entities.*;
import com.angelangelov.remont_bg.model.entities.enums.Region;
import com.angelangelov.remont_bg.model.entities.enums.ServiceOfferNames;
import com.angelangelov.remont_bg.repository.CommentRepository;
import com.angelangelov.remont_bg.repository.OfferCategoryRepository;
import com.angelangelov.remont_bg.repository.OfferRepository;
import com.angelangelov.remont_bg.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
public class OfferControllerTests {

    private String offerId;
    private String categoryId;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OfferCategoryRepository offerCategoryRepository;
    @Autowired
    private CommentRepository commentRepository;
    private static final String OFFER_CONTROLLER_PREFIX = "/offer";


    @BeforeEach
    public void setup() {
        this.init();
    }

    @Test
    @WithMockUser(username = "pesho", roles = {"USER, ADMIN"})
    public void testGetOfferCategories_ShouldWork() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/offer/categories"))
                .andExpect(status().isOk())
                .andExpect(view().name("offers/all-offer-categories"))
                .andExpect(model().attributeExists("allCategories"));
    }

    @Test
    @WithMockUser(username = "pesho", roles = {"USER, ADMIN"})
    public void testGetOffersApprovedAndActiveInCategoryShouldWork() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                OFFER_CONTROLLER_PREFIX + "/category/{id}", categoryId
        )).
                andExpect(status().isOk()).
                andExpect(view().name("offers/all-offers")).
                andExpect(model().attributeExists("offerName", "offers"));
    }

    @Test
    @WithMockUser(username = "pesho", roles = {"USER, ADMIN"})
    public void testGetSingleOfferInCategoryShouldWork() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(

                OFFER_CONTROLLER_PREFIX + "/single-offer/{id}", offerId
        )).
                andExpect(status().isOk()).
                andExpect(view().name("offers/offer-product-view")).
                andExpect(model().attributeExists("comments", "offer"));
    }

    @Test
    @WithMockUser(username = "pesho", roles = {"USER, ADMIN"})
    public void testPostCommentTestWhenValidShouldPost() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post(OFFER_CONTROLLER_PREFIX + "/single-offer/{id}", offerId)
                .param("description", "testDescription")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "pesho", roles = {"USER, ADMIN"})
    public void testGetAddOffer_ShouldWork() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/offer/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("offers/add-offer"))
                .andExpect(model().attributeExists("offerAddBindingModel"));
    }

    @Test
    @WithMockUser(username = "pesho", roles = {"USER, ADMIN"})
    public void testPostAddOfferShouldWorkWhenEverythingCorrect() throws Exception {
        InputStream is = new FileInputStream("src/test/java/com/resources/img/testImg.png");
        System.out.println();

        MockMultipartFile image = new MockMultipartFile("image", is);
        mockMvc.perform(MockMvcRequestBuilders
                .multipart("/offer/add")
                .file(image)
                .param("name", "TestName")
                .param("price", "123")
                .param("category", "Водопровдчик")
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
    public void testGetAllUserOffers_ShouldWork() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/offer/userOffers"))
                .andExpect(status().isOk())
                .andExpect(view().name("/user/all-user-offers"))
                .andExpect(model().attributeExists("allUserOffers"));
    }

    @Test
    @WithMockUser(username = "pesho", roles = {"USER, ADMIN"})
    public void testUserCanDeleteItsOwnOffer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                OFFER_CONTROLLER_PREFIX + "/user/deleteOffer/")
                .param("id", offerId)
        ).
                andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/offer/userOffers"));
    }
    @Test
    @WithMockUser(username = "pesho", roles = {"USER, ADMIN"})
    public void testGetUpdateOfferPageShouldWork() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                OFFER_CONTROLLER_PREFIX + "/update-offer/{id}", offerId
        )).
                andExpect(status().isOk()).
                andExpect(view().name("offers/update-offer")).
                andExpect(model().attributeExists("offerEditBindingModel"));
    }

    @Test
    @WithMockUser(username = "pesho", roles = {"USER, ADMIN"})
    public void testPostUpdateOfferWhenCorrectFieldsShouldWork() throws Exception {
        InputStream is = new FileInputStream("src/test/java/com/resources/img/testImg.png");
        System.out.println();
        MockMultipartFile image = new MockMultipartFile("image", is);
        mockMvc.perform(MockMvcRequestBuilders
                .multipart(OFFER_CONTROLLER_PREFIX + "/update-offer/{id}", offerId)
                .file(image)
                .param("name", "testUpdateName")
                .param("price", "123")
                .param("startsOn", "2021-03-29")
                .param("endsOn", "2022-03-25")
                .param("ownerPhoneNumber", "0881233231")
                .param("description", "description")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("offers/success-update-page"));
    }


    @Test
    @WithMockUser(username = "pesho", roles = {"USER, ADMIN"})
    public void testGetSearchPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/offer/search"))
                .andExpect(status().isOk())
                .andExpect(view().name("search-page"));

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
            Comment testComent = new Comment();
            LogEntity testLogs = new LogEntity();
            Offer testOffer = new Offer();
            testOffer.setName("testOfferName");
            testOffer.setDescription("testOfferDescription");
            testOffer.setImage("https://res.cloudinary.com/dtns2qohp/image/upload/v1615288082/depositphotos_318221368-stock-illustration-missing-picture-page-for-website_hppqti.jpg");
            testOffer.setApproved(true);
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


        }
    }
