package com.angelangelov.remont_bg.web;

import com.angelangelov.remont_bg.annotation.PageTitle;
import com.angelangelov.remont_bg.model.entities.LogEntity;
import com.angelangelov.remont_bg.model.services.OfferServiceModel;
import com.angelangelov.remont_bg.service.LogService;
import com.angelangelov.remont_bg.service.OfferService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    private final LogService logService;
    private final OfferService offerService;

    public HomeController(LogService logService, OfferService offerService) {
        this.logService = logService;
        this.offerService = offerService;
    }

    @GetMapping("/")
    @PageTitle(name = "Home")
    public String index(){
        return "index";
    }

    @GetMapping("/home")
    @PageTitle(name = "Home")
    public String home(Model model){
        List<String> mostViewed = logService.find3MostViewed();
        List<OfferServiceModel> topOffers = new ArrayList<>();
        if(mostViewed.size()<=0) {
            model.addAttribute("topOffers",new ArrayList<>());
        } else{
            for (String id : mostViewed) {
                OfferServiceModel offer = offerService.findById(id);
                Boolean active = offer.getActive();
                Boolean approved = offer.getApproved();
                if(active && approved){
                topOffers.add(offer);
                }

            }
            model.addAttribute("topOffers",topOffers);
        }



        System.out.println();
        return "home";
    }

    @GetMapping("/about")
    public String about(){
        return "about-us";
    }
}
