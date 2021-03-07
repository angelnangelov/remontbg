package com.angelangelov.remont_bg.web;

import com.angelangelov.remont_bg.service.ToolCategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tool")
public class ToolController {
private final ToolCategoryService toolCategoryService;

    public ToolController(ToolCategoryService toolCategoryService) {
        this.toolCategoryService = toolCategoryService;
    }


    @GetMapping("/categories")
    private String allOffers(Model model){


        model.addAttribute("allTools",this.toolCategoryService.getAllTools());
        return "tools/all-tools-categories";
    }

    @GetMapping("/actions")
    private String chooseAction(){
        return "tools/tool-add-or-viewall";
    }

    @GetMapping("/add")
    private String addOffer(){
        return "tools/add-tool";
    }
}
