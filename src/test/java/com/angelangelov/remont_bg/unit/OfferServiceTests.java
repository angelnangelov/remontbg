package com.angelangelov.remont_bg.unit;

import com.angelangelov.remont_bg.error.Offer.OfferWithIdNotExists;
import com.angelangelov.remont_bg.error.category.CategoryWithIdNotExists;
import com.angelangelov.remont_bg.model.entities.*;
import com.angelangelov.remont_bg.model.entities.enums.Region;
import com.angelangelov.remont_bg.model.entities.enums.ServiceOfferNames;
import com.angelangelov.remont_bg.model.services.OfferServiceModel;
import com.angelangelov.remont_bg.model.services.ToolOfferServiceModel;
import com.angelangelov.remont_bg.model.services.UserServiceModel;
import com.angelangelov.remont_bg.repository.OfferRepository;
import com.angelangelov.remont_bg.service.impl.OfferCategoryServiceImpl;
import com.angelangelov.remont_bg.service.impl.OfferServiceImpl;
import com.angelangelov.remont_bg.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class OfferServiceTests {
    @InjectMocks
    OfferServiceImpl offerService;
    @Mock
    UserServiceImpl userService;
    @Mock
    OfferCategoryServiceImpl offerCategoryService;
    @Mock
    OfferRepository offerRepository;
    @Mock
    ModelMapper modelMapper;


    Offer offer;
    OfferCategory category;
    OfferServiceModel offerServiceModel;
    User user;
    UserServiceModel userServiceModel;

    @Before
    public void initTests() {
        offer = new Offer();

        offerServiceModel = new OfferServiceModel();
        offerServiceModel.setName("Name");
        offerServiceModel.setDescription("Description");
        offerServiceModel.setStartsOn(LocalDate.now());
        offerServiceModel.setEndsOn(LocalDate.now());
        offerServiceModel.setPrice(BigDecimal.ZERO);
        offerServiceModel.setRegion(Region.Благоевград);
        offerServiceModel.setOwnerPhoneNumber("00001212");
        offerServiceModel.setActive(true);
        offerServiceModel.setApproved(false);
        offerServiceModel.setComments(new ArrayList<>());
        user = new User();
        user.setId("id");
        user.setUsername("1");
        userServiceModel = new UserServiceModel();
        userServiceModel.setId("id");
        category = new OfferCategory();
        category.setName(ServiceOfferNames.Aрхитект);
        offerServiceModel.setCategory("Измервателни");
        offer.setCategory(new OfferCategory(ServiceOfferNames.Aрхитект, "test"));
        offer.setComments(new ArrayList<>());

        offerServiceModel.setUser(userServiceModel);
    }


    @Test
    public void unapprovedOffer_WhenNotEmpty_ShouldWork() {
        List<Offer> offers = new ArrayList<>();
        offers.add(offer);
        System.out.println();
        Mockito.when(offerRepository.findAllByApprovedFalseAndActiveTrue())
                .thenReturn(offers);
        Mockito.when(modelMapper.map(offer, OfferServiceModel.class))
                .thenReturn(offerServiceModel);
        int result = offerService.findAllOffers().size();
        System.out.println();
        assertEquals(offers.size(), result);
    }

    @Test
    public void unapprovedOffer_WhenEmpty_ShouldWork() {
        List<Offer> offers = new ArrayList<>();
        Mockito.when(offerRepository.findAllByApprovedFalseAndActiveTrue())
                .thenReturn(offers);
        List<OfferServiceModel> result = offerService.findAllOffers();
        assertEquals(offers.size(), result.size());
    }

    @Test
    public void findOfferById_WhenExists_ShouldWork() {
        Mockito.when(offerRepository.findById("id"))
                .thenReturn(Optional.of(offer));
        Mockito.when(modelMapper.map(offer, OfferServiceModel.class))
                .thenReturn(offerServiceModel);
        OfferServiceModel result = offerService.findById("id");
        Mockito.verify(offerRepository).findById("id");
        Mockito.verify(modelMapper).map(offer, OfferServiceModel.class);
        assertEquals(offerServiceModel, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findById_nullInput_throws() {
        Mockito.when(offerRepository.findById(null))
                .thenThrow(new IllegalArgumentException());

        offerService.findById(null);
    }

    @Test(expected = CategoryWithIdNotExists.class)
    public void findById_invalidId_throws() {
        Mockito.when(offerRepository.findById("id"))
                .thenReturn(Optional.empty());
        offerService.findById("id");
    }

    @Test(expected = OfferWithIdNotExists.class)
    public void deleteById_WhenIdNotExists_ShouldThrow() {
        String id = "as";
        offerService.deleteOffer(id);
        Mockito.verify(offerRepository, times(1)).deleteById(id);
    }


}



