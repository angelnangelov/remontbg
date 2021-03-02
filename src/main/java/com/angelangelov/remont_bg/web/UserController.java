package com.angelangelov.remont_bg.web;

import com.angelangelov.remont_bg.model.bindings.UserRegisterBindingModel;
import com.angelangelov.remont_bg.model.services.UserServiceModel;
import com.angelangelov.remont_bg.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "auth/login";
    }


    @GetMapping("/register")
    public String userRegister(Model model) {
        if (!model.containsAttribute("userRegisterBindingModel")) {
            model.addAttribute("userRegisterBindingModel", new UserRegisterBindingModel());
        }
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerConfirm(@Valid @ModelAttribute("userRegisterBindingModel")UserRegisterBindingModel userRegisterBindingModel,
                                  BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if (!userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
            bindingResult.rejectValue("password", "error.userRegisterBindingModel", "Паролите не съвпадат!");
        }

        if (this.userService.existByUsername(userRegisterBindingModel.getUsername())) {
            bindingResult.rejectValue("username", "error.userRegisterBindingModel", "Потребител с това име вече съществува!");
        }
        if (this.userService.existByEmail(userRegisterBindingModel.getEmail())) {
            bindingResult.rejectValue("email", "error.userRegisterBindingModel", "Потребител с този емайл вече съществува!");
        }
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);
            redirectAttributes.addFlashAttribute("userRegisterBindingModel",userRegisterBindingModel);
            return "redirect:register";
        }

        this.userService.register(this.modelMapper.map(userRegisterBindingModel, UserServiceModel.class));
        return "redirect:login";
    }
}
