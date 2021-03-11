package com.angelangelov.remont_bg.web;

import com.angelangelov.remont_bg.model.bindings.UserEditBindingModel;
import com.angelangelov.remont_bg.model.bindings.UserRegisterBindingModel;
import com.angelangelov.remont_bg.model.services.UserServiceModel;
import com.angelangelov.remont_bg.model.views.UserViewModel;
import com.angelangelov.remont_bg.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder encoder;

    public UserController(UserService userService, ModelMapper modelMapper, PasswordEncoder encoder) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.encoder = encoder;
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
    public String registerConfirm(@Valid @ModelAttribute("userRegisterBindingModel") UserRegisterBindingModel userRegisterBindingModel,
                                  BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (!userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
            bindingResult.rejectValue("password", "error.userRegisterBindingModel", "Паролите не съвпадат!");
        }

        if (this.userService.existByUsername(userRegisterBindingModel.getUsername())) {
            bindingResult.rejectValue("username", "error.userRegisterBindingModel", "Потребител с това име вече съществува!");
        }
        if (this.userService.existByEmail(userRegisterBindingModel.getEmail())) {
            bindingResult.rejectValue("email", "error.userRegisterBindingModel", "Потребител с този емайл вече съществува!");
        }
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
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



    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile(Model model, Principal principal) {
        UserServiceModel userServiceModel = userService.findUserByUsername(principal.getName());
        UserViewModel user = modelMapper.map(userServiceModel, UserViewModel.class);
        model.addAttribute("userProfile",user);
        return "user-profile";
    }

    @PostMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public String profileEdit(@ModelAttribute("userEditBindingModel") UserEditBindingModel userEditBindingModel,
                              Principal principal) {


        UserServiceModel userServiceModel = modelMapper.map(userEditBindingModel, UserServiceModel.class);

        this.userService.updateProfile(userServiceModel,principal.getName());

        //TODO VALIDAIONS
        userService.updateProfile(userServiceModel, principal.getName());
        return "redirect:profile";
    }

    @GetMapping("/passwordChange")
    @PreAuthorize("isAuthenticated()")
    public String changePassword(Model model){
        if(!model.containsAttribute("userEditBindingModel")){
            model.addAttribute("userEditBindingModel",new UserEditBindingModel());
        }
        return "passwordChange";
    }

    @PostMapping("/passwordChange")
    @PreAuthorize("isAuthenticated()")
    public String changePasswordConfirm(@Valid @ModelAttribute("userEditBindingModel")
                                                    UserEditBindingModel userEditBindingModel,
                                      BindingResult  bindingResult,RedirectAttributes redirectAttributes,Principal principal)
            {

                if (!userEditBindingModel.getPassword().equals(userEditBindingModel.getConfirmPassword())) {
                    bindingResult.rejectValue("confirmPassword", "error.userEditBindingModel", "Паролите не съвпадат!");
                    return "redirect:passwordChange";

                }
                if(userEditBindingModel.getPassword().isBlank()||userEditBindingModel.getOldPassword().isBlank() ||userEditBindingModel.getConfirmPassword().isBlank()){
                    bindingResult.rejectValue("password", "error.userEditBindingModel", "Всички полета трябва да са запълнени!");
                    return "redirect:passwordChange";
                }

                if (!this.encoder.matches(userEditBindingModel.getOldPassword(), userService.findUserByUsername(principal.getName()).getPassword())) {
                    bindingResult.rejectValue("oldPassword", "error.userEditBindingModel", "Грешна парола!");
                    return "redirect:passwordChange";
                }
                    String oldPassword = userEditBindingModel.getOldPassword();

                if(userEditBindingModel.getPassword().equals(userEditBindingModel.getConfirmPassword())){
                    UserServiceModel user = modelMapper.map(userEditBindingModel, UserServiceModel.class);
                    userService.changePassword(user,userEditBindingModel.getOldPassword(),principal.getName());
                    return "redirect:/logout";
                }else{
                    //TODO VALIDATIONS
                    return "redirect:passwordChange";
                }


            }


}
