package com.angelangelov.remont_bg.web;

import com.angelangelov.remont_bg.model.bindings.ToolOfferAddBindingModel;
import com.angelangelov.remont_bg.model.entities.Offer;
import com.angelangelov.remont_bg.model.entities.ToolOffer;
import com.angelangelov.remont_bg.model.entities.enums.Region;
import com.angelangelov.remont_bg.model.entities.enums.ToolsCategoryName;
import com.angelangelov.remont_bg.model.services.OfferCategoryServiceModel;
import com.angelangelov.remont_bg.model.services.ToolCategoryServiceModel;
import com.angelangelov.remont_bg.model.services.ToolOfferServiceModel;
import com.angelangelov.remont_bg.model.views.OfferCategoryViewModel;
import com.angelangelov.remont_bg.model.views.ToolsCategoryViewModel;
import com.angelangelov.remont_bg.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import static com.angelangelov.remont_bg.web.constants.ControllersConstants.N0_IMG_URL;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/tool")
public class ToolController {
    private final ToolCategoryService toolCategoryService;
    private final ToolOfferService toolOfferService;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;

    public ToolController(ToolCategoryService toolCategoryService, ToolOfferService toolOfferService, ModelMapper modelMapper, CloudinaryService cloudinaryService) {
        this.toolCategoryService = toolCategoryService;
        this.toolOfferService = toolOfferService;

        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
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
    private String addOffer(Model model) {
        if (!model.containsAttribute("toolOfferAddBindingModel")) {
            model.addAttribute("toolOfferAddBindingModel", new ToolOfferAddBindingModel());
            model.addAttribute("categories", ToolsCategoryName.values());
            model.addAttribute("regions", Region.values());
        }
        return "tools/add-tool";
    }

    @PostMapping("/add")
    private String offerConfirm(@Valid @ModelAttribute("toolOfferAddBindingModel") ToolOfferAddBindingModel toolOfferAddBindingModel, BindingResult bindingResult,
                                RedirectAttributes redirectAttributes, Principal principal) throws IOException {


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("toolOfferAddBindingModel", toolOfferAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.toolOfferAddBindingModel"
                    , bindingResult);
            redirectAttributes.addFlashAttribute("categories", ToolsCategoryName.values());
            redirectAttributes.addFlashAttribute("regions", Region.values());
            return "redirect:add";
        }

        String username = principal.getName();
        ToolOfferServiceModel toolOfferServiceModel = modelMapper.map(toolOfferAddBindingModel, ToolOfferServiceModel.class);
        if (toolOfferAddBindingModel.getImage().isEmpty()) {
            toolOfferServiceModel.setImage(N0_IMG_URL);
        }else {
            toolOfferServiceModel.setImage(cloudinaryService.uploadImg(toolOfferAddBindingModel.getImage()));
        }
        toolOfferService.save(toolOfferServiceModel, username);

        return "redirect:all";


    }


    @GetMapping("/category/{id}")
    public String offersInCategory(@PathVariable String id, Model model){
        ToolCategoryServiceModel toolCategoryServiceModel = toolCategoryService.findById(id);
        ToolsCategoryViewModel toolsCategoryViewModel = modelMapper.map(toolCategoryServiceModel, ToolsCategoryViewModel.class);
        List<ToolOffer> tools = toolsCategoryViewModel.getTools();
        System.out.println();
        model.addAttribute("toolName",toolCategoryServiceModel.getName());
        model.addAttribute("tools",tools);
        return "tools/all-tools";
    }
}
