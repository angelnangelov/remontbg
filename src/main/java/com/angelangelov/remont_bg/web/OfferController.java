package com.angelangelov.remont_bg.web;

import com.angelangelov.remont_bg.model.bindings.OfferAddBindingModel;
import com.angelangelov.remont_bg.model.entities.enums.Region;
import com.angelangelov.remont_bg.model.entities.enums.ServiceOfferNames;
import com.angelangelov.remont_bg.model.services.OfferServiceModel;
import com.angelangelov.remont_bg.service.CloudinaryService;
import com.angelangelov.remont_bg.service.OfferCategoryService;
import com.angelangelov.remont_bg.service.OfferService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
@Controller
@RequestMapping("/offer")
public class OfferController {
    private static final String N0_IMG_URL = "https://res.cloudinary.com/dtns2qohp/image/upload/v1615288082/depositphotos_318221368-stock-illustration-missing-picture-page-for-website_hppqti.jpg";
private final OfferService offerService;
private final ModelMapper modelMapper;
private final CloudinaryService cloudinaryService;
private final OfferCategoryService offerCategoryService;
@Autowired
    public OfferController(OfferService offerService, ModelMapper modelMapper, CloudinaryService cloudinaryService, OfferCategoryService offerCategoryService) {
        this.offerService = offerService;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
    this.offerCategoryService = offerCategoryService;
}

    @GetMapping("/categories")
    private String allOffers(Model model) {
        model.addAttribute("allCategories", this.offerCategoryService.getAllCategories());
        return "offers/all-offer-categories";
    }

    @GetMapping("/actions")
    private String chooseAction() {
        return "offers/offer-add-or-viewall";
    }

    @GetMapping("/add")
    private String addOffer(Model model) {
        if (!model.containsAttribute("offerAddBindingModel")) {
            model.addAttribute("offerAddBindingModel", new OfferAddBindingModel());
            model.addAttribute("categories", ServiceOfferNames.values());
            model.addAttribute("regions", Region.values());
        }
        return "offers/add-offer";
    }

    @PostMapping("/add")
    private String offerConfirm(@Valid @ModelAttribute("offerAddBindingModel") OfferAddBindingModel offerAddBindingModel, BindingResult bindingResult,
                                RedirectAttributes redirectAttributes, Principal principal) throws IOException {


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("offerAddBindingModel", offerAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.offerAddBindingModel"
                    , bindingResult);
            redirectAttributes.addFlashAttribute("categories", ServiceOfferNames.values());
            redirectAttributes.addFlashAttribute("regions", Region.values());
            return "redirect:add";
        }
        String username = principal.getName();
        OfferServiceModel offerServiceModel = modelMapper.map(offerAddBindingModel, OfferServiceModel.class);
        if (offerAddBindingModel.getImage().isEmpty()) {
            offerServiceModel.setImage(N0_IMG_URL);
        }else {
            offerServiceModel.setImage(cloudinaryService.uploadImg(offerAddBindingModel.getImage()));
        }
        offerService.save(offerServiceModel, username);

        return "redirect:all";


    }


    @GetMapping("/all")
    private String allOffers() {
        return "offers/all-offers";
    }

}
