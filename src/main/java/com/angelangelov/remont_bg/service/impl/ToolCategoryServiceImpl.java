package com.angelangelov.remont_bg.service.impl;

import com.angelangelov.remont_bg.model.entities.ToolCategory;
import com.angelangelov.remont_bg.model.entities.enums.ToolsCategoryName;
import com.angelangelov.remont_bg.model.views.OfferCategoryViewModel;
import com.angelangelov.remont_bg.model.views.ToolsCategoryViewModel;
import com.angelangelov.remont_bg.repository.ToolCategoryRepository;
import com.angelangelov.remont_bg.service.ToolCategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ToolCategoryServiceImpl implements ToolCategoryService {
    private final ToolCategoryRepository toolCategoryRepository;
    private final ModelMapper modelMapper;

    public ToolCategoryServiceImpl(ToolCategoryRepository toolCategoryRepository, ModelMapper modelMapper) {
        this.toolCategoryRepository = toolCategoryRepository;
        this.modelMapper = modelMapper;
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

    @Override
    public List<ToolsCategoryViewModel> getAllTools() {
        return this.toolCategoryRepository.findAll().stream().map(
                a ->{
                    ToolsCategoryViewModel toolsCategoryViewModel = this.modelMapper.map(a, ToolsCategoryViewModel.class);

                    toolsCategoryViewModel.setTools(a.getTools());
                    return toolsCategoryViewModel;
                }
        ).collect(Collectors.toList());
    }
}
