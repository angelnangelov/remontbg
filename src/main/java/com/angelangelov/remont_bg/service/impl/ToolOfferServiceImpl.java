package com.angelangelov.remont_bg.service.impl;

import com.angelangelov.remont_bg.error.category.CategoryWithIdNotExists;
import com.angelangelov.remont_bg.model.entities.Offer;
import com.angelangelov.remont_bg.model.entities.ToolOffer;
import com.angelangelov.remont_bg.model.entities.User;
import com.angelangelov.remont_bg.model.entities.enums.ServiceOfferNames;
import com.angelangelov.remont_bg.model.entities.enums.ToolsCategoryName;
import com.angelangelov.remont_bg.model.services.OfferServiceModel;
import com.angelangelov.remont_bg.model.services.ToolOfferServiceModel;
import com.angelangelov.remont_bg.model.services.UserServiceModel;
import com.angelangelov.remont_bg.repository.ToolOfferRepository;
import com.angelangelov.remont_bg.service.ToolCategoryService;
import com.angelangelov.remont_bg.service.ToolOfferService;
import com.angelangelov.remont_bg.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public ToolOfferServiceModel findById(String id) {

        ToolOffer offer = toolOfferRepository.findById(id)
                .orElseThrow(() -> new CategoryWithIdNotExists(String.format("Offer with id: %s not found",id)));;

        return modelMapper.map(offer, ToolOfferServiceModel.class);    }

    @Override
    public List<ToolOfferServiceModel> findAllUnapprovedTools() {
        List<ToolOffer> allTools = toolOfferRepository.findAllByApprovedFalse();
        return allTools.stream().map(offer -> {
            ToolOfferServiceModel toolOfferServiceModel = modelMapper.map(offer, ToolOfferServiceModel.class);
            toolOfferServiceModel.setCategory(offer.getToolCategory().getName().toString());
            return toolOfferServiceModel;
        }).collect(Collectors.toList());
    }



    @Override
    public void approveTool(String id) {
        ToolOffer offer = toolOfferRepository.findById(id)
                .orElseThrow(() -> new CategoryWithIdNotExists(String.format("Offer with id: %s not found",id)));;
        ToolOfferServiceModel toolOfferServiceModel = modelMapper.map(offer,ToolOfferServiceModel.class);
        toolOfferServiceModel.setApproved(true);

        ToolOffer approvedOffer = modelMapper.map(toolOfferServiceModel, ToolOffer.class);
        approvedOffer.setToolCategory(offer.getToolCategory());
        toolOfferRepository.saveAndFlush(approvedOffer);
    }

    @Override
    public void deleteTool(String id) {
        ToolOffer offer = toolOfferRepository.findById(id)
                .orElseThrow(() -> new CategoryWithIdNotExists(String.format("Offer with id: %s not found",id)));;
        this.toolOfferRepository.delete(offer);
    }

    @Override
    public List<ToolOfferServiceModel> findAllUserTools(String name) {
        UserServiceModel user = userService.findUserByUsername(name);

        List<ToolOffer> offersByUser = toolOfferRepository.findAllByUserId(user.getId());
        return  offersByUser.stream().map(tool -> {
            ToolOfferServiceModel toolOfferServiceModel = modelMapper.map(tool, ToolOfferServiceModel.class);
            toolOfferServiceModel.setCategory(tool.getToolCategory().getName().toString());
            return toolOfferServiceModel;
        }).collect(Collectors.toList());
    }
}
