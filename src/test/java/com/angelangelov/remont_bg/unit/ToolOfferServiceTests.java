package com.angelangelov.remont_bg.unit;

import com.angelangelov.remont_bg.error.Offer.ToolWithIdNotExists;
import com.angelangelov.remont_bg.error.category.CategoryWithIdNotExists;
import com.angelangelov.remont_bg.model.entities.ToolCategory;
import com.angelangelov.remont_bg.model.entities.ToolOffer;
import com.angelangelov.remont_bg.model.entities.User;
import com.angelangelov.remont_bg.model.entities.enums.Region;
import com.angelangelov.remont_bg.model.entities.enums.ToolsCategoryName;
import com.angelangelov.remont_bg.model.services.ToolOfferServiceModel;
import com.angelangelov.remont_bg.model.services.UserServiceModel;
import com.angelangelov.remont_bg.repository.ToolOfferRepository;
import com.angelangelov.remont_bg.service.ToolCategoryService;
import com.angelangelov.remont_bg.service.impl.ToolOfferServiceImpl;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ToolOfferServiceTests {
    @InjectMocks
    ToolOfferServiceImpl toolOfferService;
    @Mock
    UserServiceImpl userService;
    @Mock
    ToolCategoryService toolCategoryService;
    @Mock
    ToolOfferRepository toolOfferRepository;
    @Mock
    ModelMapper modelMapper;


    ToolOffer toolOffer;
    ToolCategory toolCategory;
    ToolOfferServiceModel model;
    User user;
    UserServiceModel userServiceModel;

    @Before
    public void initTests() {
        toolOffer = new ToolOffer();
        toolOffer.setBrand("brand");
        model = new ToolOfferServiceModel();
        model.setName("Name");
        model.setDescription("Description");
        model.setStartsOn(LocalDate.now());
        model.setEndsOn(LocalDate.now());
        model.setPrice(BigDecimal.ZERO);
        model.setRegion(Region.Благоевград);
        model.setOwnerPhoneNumber("00001212");
        model.setActive(true);
        model.setApproved(false);
        user = new User();
        user.setId("id");
        user.setUsername("1");
        userServiceModel = new UserServiceModel();
        userServiceModel.setId("id");
        toolCategory = new ToolCategory();
        toolCategory.setName(ToolsCategoryName.Измервателни);
        model.setCategory("Измервателни");
        toolOffer.setToolCategory(new ToolCategory(ToolsCategoryName.Измервателни, "test"));


        model.setUser(userServiceModel);
    }

    @Test
    public void saveToolOffer_WhenValid_ShouldSave() {
        Mockito.when(modelMapper.map(model, ToolOffer.class))
                .thenReturn(toolOffer);
        Mockito.when(modelMapper.map(toolOffer, ToolOfferServiceModel.class))
                .thenReturn(model);

        Mockito.when(userService.findUserByUsername("name"))
                .thenReturn(userServiceModel);
        Mockito.when(toolOfferRepository.save(toolOffer)).thenReturn(toolOffer);
        ToolOfferServiceModel result = toolOfferService.save(model, "name");
        Mockito.verify(modelMapper).map(model, ToolOffer.class);
        Mockito.verify(toolOfferRepository).save(toolOffer);
        Mockito.verify(modelMapper).map(toolOffer, ToolOfferServiceModel.class);
        assertEquals(model, result);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void saveTools_WhenUserNotValid_ShouldThrow() {
        Mockito.when(modelMapper.map(model, ToolOffer.class))
                .thenReturn(toolOffer);
        Mockito.when(userService.findUserByUsername("name"))
                .thenThrow(UsernameNotFoundException.class);
        ToolOfferServiceModel result = toolOfferService.save(model, "name");
    }

    @Test
    public void unapprovedTools_WhenNotEmpty_ShouldWork() {
        List<ToolOffer> tools = new ArrayList<>();
        tools.add(toolOffer);
        System.out.println();
        Mockito.when(toolOfferRepository.findAllByApprovedFalseAndActiveTrue())
                .thenReturn(tools);
        Mockito.when(modelMapper.map(toolOffer, ToolOfferServiceModel.class))
                .thenReturn(model);
        int result = toolOfferService.findAllUnapprovedTools().size();
        System.out.println();
        assertEquals(tools.size(), result);
    }
    @Test
    public void unapprovedTools_WhenEmpty_ShouldWork() {
        List<ToolOffer> tools = new ArrayList<>();
        Mockito.when(toolOfferRepository.findAllByApprovedFalseAndActiveTrue())
                .thenReturn(tools);
        List<ToolOfferServiceModel> result = toolOfferService.findAllUnapprovedTools();
        assertEquals(tools.size(), result.size());
    }

    @Test
    public void findToolById_WhenExists_ShouldWork() {
        Mockito.when(toolOfferRepository.findById("id"))
                .thenReturn(Optional.of(toolOffer));
        Mockito.when(modelMapper.map(toolOffer, ToolOfferServiceModel.class))
                .thenReturn(model);
        ToolOfferServiceModel result = toolOfferService.findById("id");
        Mockito.verify(toolOfferRepository).findById("id");
        Mockito.verify(modelMapper).map(toolOffer, ToolOfferServiceModel.class);
        assertEquals(model, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findById_nullInput_throws() {
        Mockito.when(toolOfferRepository.findById(null))
                .thenThrow(new IllegalArgumentException());

        toolOfferService.findById(null);
    }

    @Test(expected = CategoryWithIdNotExists.class)
    public void findById_invalidId_throws() {
        Mockito.when(toolOfferRepository.findById("id"))
                .thenReturn(Optional.empty());
        toolOfferService.findById("id");
    }

    @Test
    public void extractAllByUserId_WhenUserIdValid_ShouldWork() {
//        List<ToolOffer> tools = new ArrayList<>();
//        tools.add(toolOffer);
//
//        Mockito.when(toolOfferRepository.findAllByUser_Username("name"))
//                .thenReturn(tools);
//        Mockito.when(userService.findUserByUsername("name")).thenReturn(userServiceModel);
//        Mockito.when(modelMapper.map(userServiceModel,User.class)).thenReturn(user);
//        Mockito.when(modelMapper.map(toolOffer, ToolOfferServiceModel.class))
//                .thenReturn(model);
//        String name = model.getName();
//        int result = toolOfferService.findAllUserTools(name).size();
//        assertEquals(tools.size(), result);
        //TODO :fIX

    }




    @Test(expected = ToolWithIdNotExists.class)
    public void deleteById_WhenIdNotExists_ShouldThrow() {
        String id = "as";
        toolOfferService.deleteTool(id);
        Mockito.verify(toolOfferRepository, times(1)).deleteById(id);
    }

    //TODO : USERS TOOLS AND UPDATE TOOL IMPL


}



