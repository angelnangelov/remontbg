package com.angelangelov.remont_bg.web;

import com.angelangelov.remont_bg.annotation.PageTitle;

import com.angelangelov.remont_bg.model.bindings.ToolEditBindingModel;
import com.angelangelov.remont_bg.model.bindings.ToolOfferAddBindingModel;

import com.angelangelov.remont_bg.model.entities.ToolOffer;
import com.angelangelov.remont_bg.model.entities.enums.Region;
import com.angelangelov.remont_bg.model.entities.enums.ToolsCategoryName;
import com.angelangelov.remont_bg.model.services.ToolCategoryServiceModel;
import com.angelangelov.remont_bg.model.services.ToolOfferServiceModel;
import com.angelangelov.remont_bg.model.views.*;
import com.angelangelov.remont_bg.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
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
import java.util.stream.Collectors;

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

    @PageTitle(name = "Offers - Categories")
    @GetMapping("/categories")
    @PreAuthorize("isAuthenticated()")
    public String allTools(Model model){
        int allToolsSum = this.toolCategoryService.getAllTools().stream().mapToInt(tool -> tool.getTools().size()).sum();

        model.addAttribute("allTools",this.toolCategoryService.getAllTools());
        model.addAttribute("allToolsSum",allToolsSum);
        return "tools/all-tools-categories";
    }


    @PageTitle(name = "Tool: Add")
    @GetMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public String addTool(Model model) {
        if (!model.containsAttribute("toolOfferAddBindingModel")) {
            model.addAttribute("toolOfferAddBindingModel", new ToolOfferAddBindingModel());
            model.addAttribute("categories", ToolsCategoryName.values());
            model.addAttribute("regions", Region.values());
        }
        return "tools/add-tool";
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public String addToolConfirm(@Valid @ModelAttribute("toolOfferAddBindingModel") ToolOfferAddBindingModel toolOfferAddBindingModel, BindingResult bindingResult,
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

        return "success-add-page";


    }

    @PageTitle(name = "Tools")
    @GetMapping("/category/{id}")
    @PreAuthorize("isAuthenticated()")
    public String toolsInCategory(@PathVariable String id, Model model){
        ToolCategoryServiceModel toolCategoryServiceModel = toolCategoryService.findById(id);
        ToolsCategoryViewModel toolsCategoryViewModel = modelMapper.map(toolCategoryServiceModel, ToolsCategoryViewModel.class);
        List<ToolOffer> tools = toolsCategoryViewModel.getTools().stream().filter(t->t.getApproved() && t.getActive()).collect(Collectors.toList());
        System.out.println();
        model.addAttribute("toolName",toolCategoryServiceModel.getName());
        model.addAttribute("tools",tools);
        return "tools/all-tools";
    }

    @PageTitle(name = "Tool")
    @GetMapping("/single-tool/{id}")
    @PreAuthorize("isAuthenticated()")
    public String productPage(@PathVariable String id,Model model) {
        ToolOfferServiceModel toolOfferServiceModel = toolOfferService.findById(id);
        ToolOfferViewModel toolOfferViewModel = modelMapper.map(toolOfferServiceModel, ToolOfferViewModel.class);
        toolOfferViewModel.setUser(modelMapper.map(toolOfferServiceModel.getUser(),UserViewModel.class));

        model.addAttribute("toolOffer",toolOfferViewModel);
        return "tools/tool-product-view";
    }

    @PageTitle(name = "User: Tools")
    @GetMapping("/userTools")
    @PreAuthorize("isAuthenticated()")
    public String userTools(Model model,Principal principal){
        List<ToolOfferServiceModel> allUserTools = toolOfferService.findAllUserTools(principal.getName());
        List<UserToolsViewModel> userToolsViewModels = allUserTools.stream().map(t -> {
            return modelMapper.map(t, UserToolsViewModel.class);
        }).collect(Collectors.toList());
        System.out.println();
        model.addAttribute("allUserTools",userToolsViewModels);

        return "/user/all-user-tools";
    }
    @GetMapping("/user/deleteTool/")
    @PreAuthorize("isAuthenticated()")
    public String deleteTool(@RequestParam(name = "id") String id,Principal principal){
        String username = toolOfferService.findById(id).getUser().getUsername();
        if(principal.getName().equals(username)){
            this.toolOfferService.deleteTool(id);
        }else {
            throw new UnsupportedOperationException("You cant delete other users offers");
        }

        return "redirect:/tool/userTools";
    }

    @PageTitle(name = "Tool: Update")
    @GetMapping("/update-tool/{id}")
    @PreAuthorize("isAuthenticated()")
    public String updateTool(@PathVariable String id,Model model) {
        ToolOfferServiceModel tool = toolOfferService.findById(id);
        ToolEditBindingModel toolEditBindingModel = modelMapper.map(tool, ToolEditBindingModel.class);
        if(!model.containsAttribute("toolEditBindingModel")){
            model.addAttribute("toolEditBindingModel",toolEditBindingModel);

        }
        return "tools/update-tool";
    }
    @PostMapping(value = "/update-tool/{id}")
    @PreAuthorize("isAuthenticated()")
    public String updateToolConfirm(@PathVariable String id, @Valid @ModelAttribute("toolEditBindingModel")
            ToolEditBindingModel toolEditBindingModel,
                                     BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal) throws IOException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("toolEditBindingModel", toolEditBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.toolEditBindingModel"
                    , bindingResult);

            return "redirect:/tool/update-tool/" + id;
        }
        ToolOfferServiceModel toolOfferServiceModel = modelMapper.map(toolEditBindingModel, ToolOfferServiceModel.class);
        String username = toolOfferService.findById(id).getUser().getUsername();
        if(principal.getName().equals(username)){
            if (toolEditBindingModel.getImage().isEmpty()) {
                toolOfferServiceModel.setImage(N0_IMG_URL);
            } else {
                toolOfferServiceModel.setImage(cloudinaryService.uploadImg(toolEditBindingModel.getImage()));
            }
            this.toolOfferService.updateTool(toolOfferServiceModel,id);
        }else {
            throw new UnsupportedOperationException("You cant delete other users tools");

        }

        return "tools/success-updateTool-page";
    }



}
