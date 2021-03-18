package com.angelangelov.remont_bg.web;

import com.angelangelov.remont_bg.annotation.PageTitle;
import com.angelangelov.remont_bg.model.bindings.IssueAddBindingModel;
import com.angelangelov.remont_bg.model.services.IssueServiceModel;
import com.angelangelov.remont_bg.service.CloudinaryService;
import com.angelangelov.remont_bg.service.IssueService;
import org.modelmapper.ModelMapper;
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

import static com.angelangelov.remont_bg.web.constants.ControllersConstants.N0_IMG_URL;

@Controller
@RequestMapping("issue")
public class IssueController {
    private final ModelMapper modelMapper;
    private final IssueService issueService;
    private final CloudinaryService cloudinaryService;


    public IssueController(ModelMapper modelMapper, IssueService issueService, CloudinaryService cloudinaryService) {
        this.modelMapper = modelMapper;
        this.issueService = issueService;
        this.cloudinaryService = cloudinaryService;
    }
    @PageTitle(name = "Issue")
    @GetMapping("/submit")
    public String submitIssue(Model model){
        if (!model.containsAttribute("issueAddBindingModel")) {
            model.addAttribute("issueAddBindingModel", new IssueAddBindingModel());
        }
        return "/issue/issue-page";
    }


    @PostMapping("/submit")
    private String submitIssueConfirm(@Valid @ModelAttribute("issueAddBindingModel") IssueAddBindingModel issueAddBindingModel, BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes, Principal principal) throws IOException {


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("issueAddBindingModel", issueAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.issueAddBindingModel"
                    , bindingResult);
            return "redirect:submit";
        }
        IssueServiceModel issueServiceModel = modelMapper.map(issueAddBindingModel, IssueServiceModel.class);
        if (issueAddBindingModel.getProblemImgUrl().isEmpty()) {
            issueServiceModel.setProblemImgUrl(N0_IMG_URL);
        } else {
            issueServiceModel.setProblemImgUrl(cloudinaryService.uploadImg(issueAddBindingModel.getProblemImgUrl()));
        }
        issueService.submitIssue(issueServiceModel, principal.getName());

        return "/issue/success-issue-page";


    }
}
