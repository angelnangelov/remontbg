package com.angelangelov.remont_bg.web;

import com.angelangelov.remont_bg.annotation.PageTitle;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @GetMapping("/")
    @PageTitle(name = "Home")
    public String index(){
        return "index";
    }

    @GetMapping("/home")
    @PageTitle(name = "Home")
    public String home(){
        return "home";
    }
}
