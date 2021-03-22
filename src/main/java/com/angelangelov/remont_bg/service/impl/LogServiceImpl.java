package com.angelangelov.remont_bg.service.impl;

import com.angelangelov.remont_bg.model.entities.LogEntity;
import com.angelangelov.remont_bg.model.entities.Offer;
import com.angelangelov.remont_bg.model.entities.User;
import com.angelangelov.remont_bg.model.services.OfferServiceModel;
import com.angelangelov.remont_bg.model.services.UserServiceModel;
import com.angelangelov.remont_bg.repository.LogRepository;
import com.angelangelov.remont_bg.service.LogService;
import com.angelangelov.remont_bg.service.OfferService;
import com.angelangelov.remont_bg.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LogServiceImpl implements LogService {
    private final LogRepository logRepository;
    private final OfferService offerService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public LogServiceImpl(LogRepository logRepository, OfferService offerService, UserService userService, ModelMapper modelMapper) {
        this.logRepository = logRepository;
        this.offerService = offerService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createLog(String action, String offerId) {
        OfferServiceModel offerServiceModel= offerService.findById(offerId);
        Offer offer = modelMapper.map(offerServiceModel, Offer.class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserServiceModel userServiceModel = userService.findUserByUsername(username);
        User user = modelMapper.map(userServiceModel, User.class);
        LogEntity logEntity = new LogEntity();
        logEntity.setOffer(offer);
        logEntity.setUser(user);
        logEntity.setAction(action);
        logEntity.setDateTime(LocalDateTime.now());
        logRepository.save(logEntity);


    }

    @Override
    public List<String> find3MostViewed() {
       return logRepository.result( );
    }
}
