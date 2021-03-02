package com.angelangelov.remont_bg.init;

import com.angelangelov.remont_bg.service.ServiceCategoryService;
import com.angelangelov.remont_bg.service.ToolCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CategoriesInitialization implements CommandLineRunner {
    private final ServiceCategoryService offerCategoryService;
    private final ToolCategoryService toolCategoryService;

    @Autowired
    public CategoriesInitialization(ServiceCategoryService offerCategoryService, ToolCategoryService toolCategoryService) {
        this.offerCategoryService = offerCategoryService;
        this.toolCategoryService = toolCategoryService;
    }

    @Override
    public void run(String... args) throws Exception {

        offerCategoryService.initCategories();
        toolCategoryService.initCategories();

    }
}
