package com.angelangelov.remont_bg.web;

import com.angelangelov.remont_bg.annotation.PageTitle;
import com.angelangelov.remont_bg.model.services.CommentServiceModel;
import com.angelangelov.remont_bg.model.services.OfferServiceModel;
import com.angelangelov.remont_bg.model.services.ToolOfferServiceModel;
import com.angelangelov.remont_bg.model.services.UserServiceModel;
import com.angelangelov.remont_bg.model.views.UserViewModel;
import com.angelangelov.remont_bg.repository.CommentRepository;
import com.angelangelov.remont_bg.service.CommentService;
import com.angelangelov.remont_bg.service.OfferService;
import com.angelangelov.remont_bg.service.ToolOfferService;
import com.angelangelov.remont_bg.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private  final OfferService offerService;
    private final ToolOfferService toolOfferService;
    private final CommentService commentService;



    public AdminController(UserService userService, ModelMapper modelMapper, OfferService offerService, ToolOfferService toolOfferService, CommentService commentService) {
        this.userService = userService;
        this.modelMapper = modelMapper;

        this.offerService = offerService;
        this.toolOfferService = toolOfferService;
        this.commentService = commentService;

    }
    @PageTitle(name = "Admin Panel")
    @GetMapping("/actions")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String adminPanel(){
        return "/admin/admin-page";
    }


    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String userPanel(Model model, Principal principal) {
        List<UserServiceModel> allUsers = userService.findAllUsers();
        allUsers.removeIf(user -> user.getUsername().equals(principal.getName()));
        List<UserViewModel> users = allUsers.stream().map(u -> {
            UserViewModel user = this.modelMapper.map(u, UserViewModel.class);
            user.setAuthorities(u.getAuthorities().stream()
                    .map(a -> a.getAuthority().substring(5)).collect(Collectors.toSet()));
            user.setId(u.getId());
            return user;
        }).collect(Collectors.toList());

        model.addAttribute("users", users);
        return "admin/all-users";
    }

    @PostMapping("/set-user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String setUser(@PathVariable String id) {

        this.userService.setUserRole(id, "user");
        return "redirect:/admin/users";


    }

    @PostMapping("/set-moderator/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String setModerator(@PathVariable String id) {
        userService.setUserRole(id, "moderator");
        return "redirect:/admin/users";

    }

    @PostMapping("/set-admin/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String setAdmin(@PathVariable String id) {
        this.userService.setUserRole(id, "admin");
        return "redirect:/admin/users";

    }

    @GetMapping("/offers")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String approveOffer(Model model){
        List<OfferServiceModel> unapprovedOffers = offerService.findAllOffers();
        model.addAttribute("unapprovedOffers",unapprovedOffers);

        return "/admin/all-offers-approve";
    }
    @GetMapping("/offers/deleteOffer/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteOffer(@RequestParam(name = "id") String id){
        this.offerService.deleteOffer(id);
        return "redirect:/admin/offers";
    }

    @GetMapping("/comments/deleteComment/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteComment(@RequestParam(name = "id") String id){
        CommentServiceModel commentById = commentService.findCommentById(id);
        String offerId = commentById.getOffer().getId();
        this.commentService.deleteComment(id);
        //TODO : ДРУГ НАЧИН ?
        return "redirect:/offer/single-offer/"+offerId;
    }

    //TODO : ДОБРА ПРАКТИКА ЛИ Е ГЕТ?
    @GetMapping("/offers/approveOffer/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String approveOffer(@RequestParam(name = "id") String id){
        this.offerService.approveOffer(id);
        return "redirect:/admin/offers";
    }


    @GetMapping("/tools")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String allUnapprovedTools(Model model){
        List<ToolOfferServiceModel> unapprovedTools = toolOfferService.findAllUnapprovedTools();
        model.addAttribute("unapprovedTools",unapprovedTools);
        return "/admin/all-tools-approve";
    }
    @GetMapping("/tools/deleteTool/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteTool(@RequestParam(name = "id") String id){
        this.toolOfferService.deleteTool(id);
        return "redirect:/admin/tools";
    }
    @GetMapping("/tools/approveTool/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String approveTool(@RequestParam(name = "id") String id){
        this.toolOfferService.approveTool(id);
        return "redirect:/admin/tools";
    }
}
