package com.angelangelov.remont_bg.web;

import com.angelangelov.remont_bg.service.OfferCategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/offer")
public class OfferController {
private final OfferCategoryService offerCategoryService;

    public OfferController(OfferCategoryService offerCategoryService) {
        this.offerCategoryService = offerCategoryService;
    }


    @GetMapping("/categories")
    private String allOffers(Model model){


      model.addAttribute("allCategories",this.offerCategoryService.getAllCategories());
       return "offers/all-offer-categories";
    }

}
