package com.angelangelov.remont_bg.service.impl;

import com.angelangelov.remont_bg.model.entities.ServiceCategory;
import com.angelangelov.remont_bg.model.entities.ToolCategory;
import com.angelangelov.remont_bg.model.entities.enums.ServiceOfferNames;
import com.angelangelov.remont_bg.model.entities.enums.ToolsCategoryName;
import com.angelangelov.remont_bg.repository.ToolCategoryRepository;
import com.angelangelov.remont_bg.service.ToolCategoryService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ToolCategoryServiceImpl implements ToolCategoryService {
    private final ToolCategoryRepository toolCategoryRepository;

    public ToolCategoryServiceImpl(ToolCategoryRepository toolCategoryRepository) {
        this.toolCategoryRepository = toolCategoryRepository;
    }

    @Override
    public void initCategories() {
        if (toolCategoryRepository.count() == 0) {
            Arrays.stream(ToolsCategoryName.values()).forEach(s -> {
                ToolCategory toolCategory = new ToolCategory(s, "Описание за категория " + s.name());
                toolCategoryRepository.save(toolCategory);

            });
        }
    }
}
