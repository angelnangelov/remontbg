package com.angelangelov.remont_bg.service;

import com.angelangelov.remont_bg.model.entities.ToolCategory;
import com.angelangelov.remont_bg.model.entities.enums.ServiceOfferNames;
import com.angelangelov.remont_bg.model.entities.enums.ToolsCategoryName;
import com.angelangelov.remont_bg.model.services.ToolCategoryServiceModel;
import com.angelangelov.remont_bg.model.views.ToolsCategoryViewModel;

import java.util.List;

public interface ToolCategoryService {
    void initCategories();

    List<ToolsCategoryViewModel> getAllTools();

    ToolCategory findByName(ToolsCategoryName name);

    ToolCategoryServiceModel findById(String id);
}
