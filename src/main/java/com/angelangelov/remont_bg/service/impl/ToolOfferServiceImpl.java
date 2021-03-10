package com.angelangelov.remont_bg.service.impl;

import com.angelangelov.remont_bg.model.entities.Offer;
import com.angelangelov.remont_bg.model.entities.ToolOffer;
import com.angelangelov.remont_bg.model.entities.User;
import com.angelangelov.remont_bg.model.entities.enums.ServiceOfferNames;
import com.angelangelov.remont_bg.model.entities.enums.ToolsCategoryName;
import com.angelangelov.remont_bg.model.services.ToolOfferServiceModel;
import com.angelangelov.remont_bg.repository.ToolOfferRepository;
import com.angelangelov.remont_bg.service.ToolCategoryService;
import com.angelangelov.remont_bg.service.ToolOfferService;
import com.angelangelov.remont_bg.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ToolOfferServiceImpl implements ToolOfferService {
    private final ModelMapper modelMapper;
    private final ToolOfferRepository toolOfferRepository;
    private final ToolCategoryService toolCategoryService;
    private final UserService userService;

    public ToolOfferServiceImpl(ModelMapper modelMapper, ToolOfferRepository toolOfferRepository, ToolCategoryService toolCategoryService, UserService userService) {
        this.modelMapper = modelMapper;
        this.toolOfferRepository = toolOfferRepository;
        this.toolCategoryService = toolCategoryService;
        this.userService = userService;
    }

    @Override
    public void save(ToolOfferServiceModel toolOfferServiceModel, String username) {
        toolOfferServiceModel.setActive(true);
        toolOfferServiceModel.setApproved(false);
        ToolOffer toolOffer= modelMapper.map(toolOfferServiceModel, ToolOffer.class);
        toolOffer.setToolCategory(toolCategoryService.findByName(ToolsCategoryName.valueOf(toolOfferServiceModel.getCategory())));
        toolOffer.setUser(modelMapper.map(userService.findUserByUsername(username), User.class));
        toolOfferRepository.save(toolOffer);

    }
}
