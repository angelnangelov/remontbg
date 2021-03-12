package com.angelangelov.remont_bg.web;

import com.angelangelov.remont_bg.model.bindings.OfferAddBindingModel;
import com.angelangelov.remont_bg.model.entities.Offer;
import com.angelangelov.remont_bg.model.entities.enums.Region;
import com.angelangelov.remont_bg.model.entities.enums.ServiceOfferNames;
import com.angelangelov.remont_bg.model.services.OfferCategoryServiceModel;
import com.angelangelov.remont_bg.model.services.OfferServiceModel;
import com.angelangelov.remont_bg.model.views.OfferCategoryViewModel;
import com.angelangelov.remont_bg.model.views.OfferViewModel;
import com.angelangelov.remont_bg.model.views.UserOffersViewModel;
import com.angelangelov.remont_bg.model.views.UserViewModel;
import com.angelangelov.remont_bg.service.CloudinaryService;
import com.angelangelov.remont_bg.service.OfferCategoryService;
import com.angelangelov.remont_bg.service.OfferService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import static com.angelangelov.remont_bg.web.constants.ControllersConstants.N0_IMG_URL;

@Controller
@RequestMapping("/offer")
public class OfferController {


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

    @GetMapping("/category/{id}")
    public String offersInCategory(@PathVariable String id,Model model){
        OfferCategoryServiceModel offerCategoryServiceModel = offerCategoryService.findById(id);
        OfferCategoryViewModel offerByCategory = modelMapper.map(offerCategoryServiceModel, OfferCategoryViewModel.class);
        List<Offer> approvedOffers = offerByCategory.getOffers().stream().filter(o -> o.getApproved()).collect(Collectors.toList());
        
        System.out.println();
        model.addAttribute("offerName",offerByCategory.getName());
        model.addAttribute("offers",approvedOffers);
        return "offers/all-offers";
    }

    @GetMapping("/single-offer/{id}")
    private String productPage(@PathVariable String id,Model model) {
        OfferServiceModel offerServiceModel = offerService.findById(id);
        OfferViewModel offerViewModel = modelMapper.map(offerServiceModel, OfferViewModel.class);
        offerViewModel.setUserViewModel(modelMapper.map(offerServiceModel.getUser(), UserViewModel.class));
        model.addAttribute("offer",offerViewModel);
        return "offers/offer-product-view";
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
        } else {
            offerServiceModel.setImage(cloudinaryService.uploadImg(offerAddBindingModel.getImage()));
        }
        offerService.save(offerServiceModel, username);

        return "success-add-page";


    }

    @GetMapping("/userOffers")
    public String userOffer(Model model,Principal principal){
        List<OfferServiceModel> allUserOffers = offerService.findAllUserOffers(principal.getName());
        List<UserOffersViewModel> userOffersViewModels = allUserOffers.stream().map(o -> {
            return modelMapper.map(o, UserOffersViewModel.class);
        }).collect(Collectors.toList());
        System.out.println();
        model.addAttribute("allUserOffers",userOffersViewModels);

        return "/user/all-user-offers";
    }
    @GetMapping("/user/deleteOffer/")
    public String deleteOffer(@RequestParam(name = "id") String id,Principal principal){
        String username = offerService.findById(id).getUser().getUsername();
        if(principal.getName().equals(username)){
            this.offerService.deleteOffer(id);
        }else {
            throw new UnsupportedOperationException("You cant delete other users offers");
        }

        return "redirect:/offer/userOffers";
    }





}
