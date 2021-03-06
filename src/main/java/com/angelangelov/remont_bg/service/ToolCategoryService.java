package com.angelangelov.remont_bg.service;

import com.angelangelov.remont_bg.model.views.ToolsCategoryViewModel;

import java.util.List;

public interface ToolCategoryService {
    void initCategories();

    List<ToolsCategoryViewModel> getAllTools();
}
