package com.angelangelov.remont_bg.service;

import com.angelangelov.remont_bg.model.entities.ToolOffer;
import com.angelangelov.remont_bg.model.services.ToolOfferServiceModel;

import java.util.List;

public interface ToolOfferService {
    ToolOfferServiceModel save(ToolOfferServiceModel toolOfferServiceModel, String username);

    ToolOfferServiceModel findById(String id);

    List<ToolOfferServiceModel> findAllUnapprovedTools();

    

    void approveTool(String id);

    void deleteTool(String id);

    List<ToolOfferServiceModel> findAllUserTools(String name);

    ToolOfferServiceModel updateTool(ToolOfferServiceModel toolOfferServiceModel, String id);
}
