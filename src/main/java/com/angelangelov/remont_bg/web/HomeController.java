package com.angelangelov.remont_bg.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @GetMapping("/")

    public String index(){
        return "index";
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }
}
