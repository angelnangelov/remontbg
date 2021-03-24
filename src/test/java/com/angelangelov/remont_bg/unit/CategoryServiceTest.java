package com.angelangelov.remont_bg.unit;

import com.angelangelov.remont_bg.error.category.CategoryWithIdNotExists;
import com.angelangelov.remont_bg.model.entities.OfferCategory;
import com.angelangelov.remont_bg.model.services.OfferCategoryServiceModel;
import com.angelangelov.remont_bg.repository.OfferCategoryRepository;
import com.angelangelov.remont_bg.service.impl.OfferCategoryServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CategoryServiceTest {

    @InjectMocks
    OfferCategoryServiceImpl categoryService;
    @Mock
    OfferCategoryRepository categoryRepository;
    @Mock
    ModelMapper modelMapper;
    OfferCategory offerCategory;
    OfferCategoryServiceModel offerCategoryServiceModel;

    @Before
    public void initTests() {
        offerCategory = mock(OfferCategory.class);
        offerCategoryServiceModel = mock(OfferCategoryServiceModel.class);
    }

    @Test
    public void initCategories_WhenNoCategories_Work() {
        List<OfferCategory> categories = new ArrayList<>();
        categories.add(new OfferCategory());
        categories.add(new OfferCategory());
        categories.add(new OfferCategory());
        Mockito.when(categoryRepository.count())
                .thenReturn(0L);
        Mockito.when(categoryRepository.findAll())
                .thenReturn(categories);
        categoryService.initCategories();
        int result = categoryRepository.findAll().size();
        assertEquals(3, result);
    }

    @Test
    public void findById_WhenCategoryExist_Work() {
        Mockito.when(categoryRepository.findById("id"))
                .thenReturn(Optional.of(offerCategory));
        Mockito.when(modelMapper.map(offerCategory, OfferCategoryServiceModel.class))
                .thenReturn(offerCategoryServiceModel);
        OfferCategoryServiceModel result = categoryService.findById("id");
        Mockito.verify(categoryRepository).findById("id");
        Mockito.verify(modelMapper).map(offerCategory, OfferCategoryServiceModel.class);
        assertEquals(offerCategoryServiceModel, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findById_nullInput_throwsIllegalArgumentException() {
        Mockito.when(categoryRepository.findById(null))
                .thenThrow(new IllegalArgumentException());
        categoryService.findById(null);
    }

    @Test(expected = CategoryWithIdNotExists.class)
    public void findCategoryById_IdNotExists_throws() {
        Mockito.when(categoryRepository.findById("id"))
                .thenReturn(Optional.empty());
        categoryService.findById("id");
    }







}