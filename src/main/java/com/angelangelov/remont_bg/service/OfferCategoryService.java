package com.angelangelov.remont_bg.service;

import com.angelangelov.remont_bg.model.views.OfferCategoryViewModel;

import java.util.List;

public interface OfferCategoryService {
    void initCategories();
    List<OfferCategoryViewModel> getAllCategories();
}
