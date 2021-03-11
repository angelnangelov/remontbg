package com.angelangelov.remont_bg.web;

import com.angelangelov.remont_bg.model.bindings.OfferAddBindingModel;
import com.angelangelov.remont_bg.model.bindings.UserEditBindingModel;
import com.angelangelov.remont_bg.model.bindings.UserPasswordChangeBindingModel;
import com.angelangelov.remont_bg.model.bindings.UserRegisterBindingModel;
import com.angelangelov.remont_bg.model.entities.User;
import com.angelangelov.remont_bg.model.entities.enums.Region;
import com.angelangelov.remont_bg.model.entities.enums.ServiceOfferNames;
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
    public String profile(Model model,  Principal principal) {

        UserServiceModel userServiceModel = userService.findUserByUsername(principal.getName());
//        UserViewModel user = modelMapper.map(userServiceModel, UserViewModel.class);
        UserEditBindingModel userEditBindingModel = modelMapper.map(userServiceModel, UserEditBindingModel.class);
        if(!model.containsAttribute("userEditBindingModel")){
            model.addAttribute("userEditBindingModel",userEditBindingModel);
        }
        model.addAttribute("username",principal.getName());


      return "user-profile";
    }

    @PostMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public String profileEdit(@Valid @ModelAttribute("userEditBindingModel") UserEditBindingModel userEditBindingModel,
                              BindingResult bindingResult, RedirectAttributes redirectAttributes,
                              Principal principal) {

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("userEditBindingModel", userEditBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userEditBindingModel"
                    , bindingResult);
            return "redirect:profile";
        }

       UserServiceModel userServiceModel = modelMapper.map(userEditBindingModel, UserServiceModel.class);


        this.userService.updateProfile(userServiceModel,principal.getName());

        userService.updateProfile(userServiceModel, principal.getName());
        return "redirect:profile";
    }

    @GetMapping("/passwordChange")
    @PreAuthorize("isAuthenticated()")
    public String changePassword(Model model){
        if(!model.containsAttribute("userPasswordChangeBindingModel")){
            model.addAttribute("userPasswordChangeBindingModel",new UserPasswordChangeBindingModel());
        }
        return "passwordChange";
    }

    @PostMapping("/passwordChange")
    @PreAuthorize("isAuthenticated()")
    public String changePasswordConfirm(@Valid @ModelAttribute("userPasswordChangeBindingModel")
                                                    UserPasswordChangeBindingModel userPasswordChangeBindingModel,
                                      BindingResult  bindingResult,RedirectAttributes redirectAttributes,Principal principal)
            {



                if (!this.encoder.matches(userPasswordChangeBindingModel.getOldPassword(), userService.findUserByUsername(principal.getName()).getPassword())) {
                    bindingResult.rejectValue("oldPassword", "error.userPasswordChangeBindingModel", "Грешна парола!");

                }
                if (!userPasswordChangeBindingModel.getPassword().equals(userPasswordChangeBindingModel.getConfirmPassword())) {
                    bindingResult.rejectValue("confirmPassword", "error.userPasswordChangeBindingModel", "Паролите не съвпадат!");

                }
                if(userPasswordChangeBindingModel.getPassword().isBlank()||userPasswordChangeBindingModel.getOldPassword().isBlank() ||userPasswordChangeBindingModel.getConfirmPassword().isBlank()){
                    bindingResult.rejectValue("password", "error.userPasswordChangeBindingModel", "Всички полета трябва да са запълнени!");

                }
                if(bindingResult.hasErrors()){
                    redirectAttributes.addFlashAttribute("userPasswordChangeBindingModel", userPasswordChangeBindingModel);
                    redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userPasswordChangeBindingModel"
                            , bindingResult);
                    return "redirect:passwordChange";
                }

                    String oldPassword = userPasswordChangeBindingModel.getOldPassword();

                if(userPasswordChangeBindingModel.getPassword().equals(userPasswordChangeBindingModel.getConfirmPassword())){
                    UserServiceModel user = modelMapper.map(userPasswordChangeBindingModel, UserServiceModel.class);
                    userService.changePassword(user,userPasswordChangeBindingModel.getOldPassword(),principal.getName());
                    return "redirect:/logout";
                }else{
                    return "redirect:passwordChange";
                }


            }

            @GetMapping("/issue")
    public String issue(){
        return "issue-page";
            }


}
