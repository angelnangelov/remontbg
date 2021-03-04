package com.angelangelov.remont_bg.web;

import com.angelangelov.remont_bg.model.bindings.UserRegisterBindingModel;
import com.angelangelov.remont_bg.model.services.UserServiceModel;
import com.angelangelov.remont_bg.model.views.UserViewModel;
import com.angelangelov.remont_bg.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/user",method = RequestMethod.GET)
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public String login(Model model) {
        return "auth/login";
    }


    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    public String userRegister(Model model) {
        if (!model.containsAttribute("userRegisterBindingModel")) {
            model.addAttribute("userRegisterBindingModel", new UserRegisterBindingModel());
        }
        return "auth/register";
    }

    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
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

        try {
            this.userService.register(this.modelMapper
                    .map(userRegisterBindingModel, UserServiceModel.class));
            return "redirect:login";
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel"
                    , bindingResult);
            return "redirect:register";
        }
    }



    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String adminPanel(Model model,Principal principal){
        List<UserServiceModel> allUsers = userService.findAllUsers();
        allUsers.removeIf(user -> user.getUsername().equals(principal.getName()));
        List<UserViewModel> users = allUsers.stream().map(u -> {
            UserViewModel user = this.modelMapper.map(u, UserViewModel.class);
            user.setAuthorities(u.getAuthorities().stream()
                    .map(a -> a.getAuthority().substring(5)).collect(Collectors.toSet()));
            user.setId(u.getId());
            return user;
        }).collect(Collectors.toList());

        model.addAttribute("users",users);
        return "admin";
    }

    @PostMapping("/set-user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String setUser(@PathVariable String id) {

        this.userService.setUserRole(id, "user");
        return "redirect:/user/admin";


    }

    @PostMapping("/set-moderator/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String setModerator(@PathVariable String id) {
        userService.setUserRole(id, "moderator");
        return "redirect:/user/admin";

    }

    @PostMapping("/set-admin/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String setAdmin(@PathVariable String id) {
        this.userService.setUserRole(id, "admin");
        return "redirect:/user/admin";

    }

}
