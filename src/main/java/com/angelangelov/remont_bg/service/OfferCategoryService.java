package com.angelangelov.remont_bg.service;

import com.angelangelov.remont_bg.model.entities.OfferCategory;
import com.angelangelov.remont_bg.model.entities.enums.ServiceOfferNames;
import com.angelangelov.remont_bg.model.services.OfferCategoryServiceModel;
import com.angelangelov.remont_bg.model.views.OfferCategoryViewModel;

import java.util.List;

public interface OfferCategoryService {
    void initCategories();
    List<OfferCategoryViewModel> getAllCategories();
    OfferCategory findByName(ServiceOfferNames name);

}
