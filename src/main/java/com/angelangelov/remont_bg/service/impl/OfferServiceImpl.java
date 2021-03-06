package com.angelangelov.remont_bg.service.impl;

import com.angelangelov.remont_bg.error.Offer.OfferWithIdNotExists;
import com.angelangelov.remont_bg.error.category.CategoryWithIdNotExists;
import com.angelangelov.remont_bg.model.entities.Offer;
import com.angelangelov.remont_bg.model.entities.User;
import com.angelangelov.remont_bg.model.entities.enums.ServiceOfferNames;
import com.angelangelov.remont_bg.model.services.CommentServiceModel;
import com.angelangelov.remont_bg.model.services.OfferServiceModel;
import com.angelangelov.remont_bg.model.services.UserServiceModel;
import com.angelangelov.remont_bg.repository.OfferRepository;
import com.angelangelov.remont_bg.service.CommentService;
import com.angelangelov.remont_bg.service.OfferCategoryService;
import com.angelangelov.remont_bg.service.OfferService;
import com.angelangelov.remont_bg.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.angelangelov.remont_bg.web.constants.ControllersConstants.N0_IMG_URL;
import static com.angelangelov.remont_bg.web.constants.ControllersConstants.PROFILE_IMG_DEFAULT;

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

    @Override
    public OfferServiceModel findById(String id) {
         Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new CategoryWithIdNotExists(String.format("Offer with id: %s not found",id)));;

        return modelMapper.map(offer, OfferServiceModel.class);
    }

    @Override
    public List<OfferServiceModel> findAllOffers() {
        List<Offer> allOffers = offerRepository.findAllByApprovedFalseAndActiveTrue();
        return allOffers.stream().map(offer -> {
            OfferServiceModel offerServiceModel = modelMapper.map(offer, OfferServiceModel.class);
            offerServiceModel.setCategory(offer.getCategory().getName().toString());
            return offerServiceModel;
        }).collect(Collectors.toList());
    }

    @Override
    public List<OfferServiceModel> findAllUserOffers(String username) {
        UserServiceModel user = userService.findUserByUsername(username);

        List<Offer> offersByUser = offerRepository.findAllByUserId(user.getId());
        return  offersByUser.stream().map(offer -> {
            OfferServiceModel offerServiceModel = modelMapper.map(offer, OfferServiceModel.class);
            offerServiceModel.setCategory(offer.getCategory().getName().toString());
            return offerServiceModel;
        }).collect(Collectors.toList());

    }

    @Override
    public void deleteOffer(String id) {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new OfferWithIdNotExists(String.format("Offer with id: %s not found",id)));


        this.offerRepository.delete(offer);
    }

    @Override
    public OfferServiceModel updateOffer(OfferServiceModel offerServiceModel,String id) {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new OfferWithIdNotExists(String.format("Offer with id: %s not found", id)));
        offer.setActive(true);
        offer.setApproved(false);
        offer.setName(offerServiceModel.getName());
        if (offer.getImage().equals(N0_IMG_URL)) {
            offer.setImage(offerServiceModel.getImage());
        }
        offer.setPrice(offerServiceModel.getPrice());
        offer.setOwnerPhoneNumber(offerServiceModel.getOwnerPhoneNumber());
        offer.setStartsOn(offerServiceModel.getStartsOn());
        offer.setEndsOn(offerServiceModel.getEndsOn());
        offer.setDescription(offerServiceModel.getDescription());
        System.out.println();
        return modelMapper.map(offerRepository.saveAndFlush(offer),OfferServiceModel.class);
    }

    @Override
    public void approveOffer(String id) {

        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new CategoryWithIdNotExists(String.format("Offer with id: %s not found",id)));;
                OfferServiceModel offerServiceModel = modelMapper.map(offer,OfferServiceModel.class);
                offerServiceModel.setApproved(true);


        Offer approvedOffer = modelMapper.map(offerServiceModel, Offer.class);
        approvedOffer.getLogs().clear();
        approvedOffer.getLogs().addAll(approvedOffer.getLogs());
        approvedOffer.setCategory(offer.getCategory());
        offerRepository.saveAndFlush(approvedOffer);

    }


}

