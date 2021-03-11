package com.angelangelov.remont_bg.service;

import com.angelangelov.remont_bg.model.services.ToolOfferServiceModel;

import java.util.List;

public interface ToolOfferService {
    void save(ToolOfferServiceModel toolOfferServiceModel, String username);

    ToolOfferServiceModel findById(String id);

    List<ToolOfferServiceModel> findAllUnapprovedTools();

    

    void approveTool(String id);

    void deleteTool(String id);
}
