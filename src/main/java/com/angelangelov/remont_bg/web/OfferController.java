package com.angelangelov.remont_bg.web;

import com.angelangelov.remont_bg.annotation.PageTitle;
import com.angelangelov.remont_bg.model.bindings.CommentAddBindingModel;
import com.angelangelov.remont_bg.model.bindings.OfferAddBindingModel;
import com.angelangelov.remont_bg.model.bindings.OfferEditBindingModel;
import com.angelangelov.remont_bg.model.bindings.UserPasswordChangeBindingModel;
import com.angelangelov.remont_bg.model.entities.Comment;
import com.angelangelov.remont_bg.model.entities.Offer;
import com.angelangelov.remont_bg.model.entities.enums.Region;
import com.angelangelov.remont_bg.model.entities.enums.ServiceOfferNames;
import com.angelangelov.remont_bg.model.services.CommentServiceModel;
import com.angelangelov.remont_bg.model.services.OfferCategoryServiceModel;
import com.angelangelov.remont_bg.model.services.OfferServiceModel;
import com.angelangelov.remont_bg.model.views.OfferCategoryViewModel;
import com.angelangelov.remont_bg.model.views.OfferViewModel;
import com.angelangelov.remont_bg.model.views.UserOffersViewModel;
import com.angelangelov.remont_bg.model.views.UserViewModel;
import com.angelangelov.remont_bg.service.CloudinaryService;
import com.angelangelov.remont_bg.service.CommentService;
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
import java.util.Set;
import java.util.stream.Collectors;

import static com.angelangelov.remont_bg.web.constants.ControllersConstants.N0_IMG_URL;

@Controller
@RequestMapping("/offer")
public class OfferController {


    private final OfferService offerService;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;
    private final OfferCategoryService offerCategoryService;
    private final CommentService commentService;

    @Autowired
    public OfferController(OfferService offerService, ModelMapper modelMapper, CloudinaryService cloudinaryService, OfferCategoryService offerCategoryService, CommentService commentService) {
        this.offerService = offerService;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
        this.offerCategoryService = offerCategoryService;
        this.commentService = commentService;
    }
    @PageTitle(name = "Offers - Categories")
    @GetMapping("/categories")
    public String allOffers(Model model) {
        int allOffersSum = this.offerCategoryService.getAllCategories().stream().mapToInt(category -> category.getOffers().size()).sum();
        model.addAttribute("allCategories", this.offerCategoryService.getAllCategories());
        model.addAttribute("allOffersSum", allOffersSum);
        return "offers/all-offer-categories";
    }
    @PageTitle(name = "Offers")
    @GetMapping("/category/{id}")
    public String offersInCategory(@PathVariable String id,Model model){
        OfferCategoryServiceModel offerCategoryServiceModel = offerCategoryService.findById(id);
        OfferCategoryViewModel offerByCategory = modelMapper.map(offerCategoryServiceModel, OfferCategoryViewModel.class);
        Set<Offer> approvedOffers = offerByCategory.getOffers().stream().filter(o -> o.getApproved() && o.getActive()).collect(Collectors.toSet());
        
        System.out.println();
        model.addAttribute("offerName",offerByCategory.getName());
        model.addAttribute("offers",approvedOffers);
        return "offers/all-offers";
    }
    @PageTitle(name = "Offer")
    @GetMapping("/single-offer/{id}")
    public String singleOffer(@PathVariable String id,Model model) {
        if(!model.containsAttribute("commentAddBindingModel")){
            model.addAttribute("commentAddBindingModel",new CommentAddBindingModel());
        }
        OfferServiceModel offerServiceModel = offerService.findById(id);
        OfferViewModel offerViewModel = modelMapper.map(offerServiceModel, OfferViewModel.class);
        offerViewModel.setUserViewModel(modelMapper.map(offerServiceModel.getUser(), UserViewModel.class));
        List<CommentServiceModel> commentsByOfferId = commentService.findCommentsByOfferId(id);

        model.addAttribute("comments",commentsByOfferId);
        model.addAttribute("offer",offerViewModel);
        return "offers/offer-product-view";
    }

    @PostMapping("/single-offer/{id}")
    public String postComment(@PathVariable String id ,@Valid
                               @ModelAttribute("commentAddBindingModel") CommentAddBindingModel commentAddBindingModel,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes, Principal principal) {
        if (commentAddBindingModel.getDescription().isBlank()) {
            bindingResult.rejectValue("description", "error.commentAddBindingModel", "Коментарът трябва да бъде минимум 4 символа!");

        }
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("commentAddBindingModel", commentAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.commentAddBindingModel"
                    , bindingResult);
            return "redirect:"+id;

        }
        OfferServiceModel offerServiceModel = offerService.findById(id);
        CommentServiceModel comment = modelMapper.map(commentAddBindingModel, CommentServiceModel.class);

        commentService.addComment(comment, principal.getName(), offerServiceModel);

        return "redirect:"+id;
    }


    @PageTitle(name = "Offer: Actions")
    @GetMapping("/actions")
    public String chooseAction() {
        return "offers/offer-add-or-viewall";
    }

    @PageTitle(name = "Offer: Add")
    @GetMapping("/add")
    public String addOffer(Model model) {
        if (!model.containsAttribute("offerAddBindingModel")) {
            model.addAttribute("offerAddBindingModel", new OfferAddBindingModel());
            model.addAttribute("categories", ServiceOfferNames.values());
            model.addAttribute("regions", Region.values());
        }
        return "offers/add-offer";
    }

    @PostMapping("/add")
    public String offerConfirm(@Valid @ModelAttribute("offerAddBindingModel") OfferAddBindingModel offerAddBindingModel, BindingResult bindingResult,
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
    @PageTitle(name = "User: Offers")
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

    @PageTitle(name = "Offer: Update")
    @GetMapping("/update-offer/{id}")
    public String updateOffer(@PathVariable String id,Model model) {
        OfferServiceModel offer = offerService.findById(id);
        OfferEditBindingModel offerEditBindingModel = modelMapper.map(offer, OfferEditBindingModel.class);
        if(!model.containsAttribute("offerEditBindingModel")){
            model.addAttribute("offerEditBindingModel",offerEditBindingModel);

        }
        return "offers/update-offer";
    }
    @PostMapping(value = "/update-offer/{id}")
    public String updateOfferConfirm(@PathVariable String id, @Valid @ModelAttribute("offerEditBindingModel")
                                                OfferEditBindingModel offerEditBindingModel,
                                        BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal) throws IOException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("offerEditBindingModel", offerEditBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.offerEditBindingModel"
                    , bindingResult);

            return "redirect:/offer/update-offer/" + id;
        }
        OfferServiceModel offerServiceModel = modelMapper.map(offerEditBindingModel, OfferServiceModel.class);
        String username = offerService.findById(id).getUser().getUsername();
        if(principal.getName().equals(username)){
            if (offerEditBindingModel.getImage().isEmpty()) {
                offerServiceModel.setImage(N0_IMG_URL);
            } else {
                offerServiceModel.setImage(cloudinaryService.uploadImg(offerEditBindingModel.getImage()));
            }
            this.offerService.updateOffer(offerServiceModel,id);
        }else {
            throw new UnsupportedOperationException("You cant delete other users offers");

        }

        return "offers/success-update-page";
    }

    @GetMapping("/search")
    public String search(){
        return "search-page";
    }
    }

