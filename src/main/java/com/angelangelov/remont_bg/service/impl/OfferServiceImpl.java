package com.angelangelov.remont_bg.service.impl;

import com.angelangelov.remont_bg.model.entities.Offer;
import com.angelangelov.remont_bg.model.entities.User;
import com.angelangelov.remont_bg.model.entities.enums.ServiceOfferNames;
import com.angelangelov.remont_bg.model.services.OfferServiceModel;
import com.angelangelov.remont_bg.repository.OfferRepository;
import com.angelangelov.remont_bg.service.OfferCategoryService;
import com.angelangelov.remont_bg.service.OfferService;
import com.angelangelov.remont_bg.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class OfferServiceImpl implements OfferService {

    private final ModelMapper modelMapper;
    private final OfferRepository offerRepository;
    private final OfferCategoryService offerCategoryService;
    private final UserService userService;

    public OfferServiceImpl(ModelMapper modelMapper, OfferRepository offerRepository, OfferCategoryService offerCategoryService, UserService userService) {
        this.modelMapper = modelMapper;
        this.offerRepository = offerRepository;
        this.offerCategoryService = offerCategoryService;
        this.userService = userService;
    }

    @Override
    public void save(OfferServiceModel offerServiceModel, String username) {
        offerServiceModel.setActive(true);
        offerServiceModel.setApproved(false);
        Offer offer = modelMapper.map(offerServiceModel, Offer.class);
        offer.setCategory(offerCategoryService.findByName(ServiceOfferNames.valueOf(offerServiceModel.getCategory())));
        offer.setUser(modelMapper.map(userService.findUserByUsername(username),User.class));
        System.out.println();
        offerRepository.saveAndFlush(offer);
        System.out.println();

    }


    }

