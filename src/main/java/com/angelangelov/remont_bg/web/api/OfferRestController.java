package com.angelangelov.remont_bg.web.api;

import com.angelangelov.remont_bg.model.views.OfferApiViewModel;
import com.angelangelov.remont_bg.repository.OfferRepository;
import com.angelangelov.remont_bg.repository.ToolOfferRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/offers")
@RestController
public class OfferRestController {
    private final OfferRepository offerRepository;
    private final ModelMapper modelMapper;

    public OfferRestController(OfferRepository offerRepository, ModelMapper modelMapper) {
        this.offerRepository = offerRepository;
        this.modelMapper = modelMapper;

    }

    @GetMapping("/api")
    public ResponseEntity<List<OfferApiViewModel>> findAll(){
        return ResponseEntity.ok().body(
                offerRepository
                        .findAllByApprovedTrueAndActiveTrue()
                        .stream()
                        .map(offer -> {
                            OfferApiViewModel offerApiViewModel = modelMapper.map(offer, OfferApiViewModel.class);
                            offerApiViewModel.setCategory(offer.getCategory().getName().toString());
                            return offerApiViewModel;
                        }).collect(Collectors.toList()));



    }

}
