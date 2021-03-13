package com.angelangelov.remont_bg.service;

import com.angelangelov.remont_bg.model.services.CommentServiceModel;
import com.angelangelov.remont_bg.model.services.OfferServiceModel;

public interface CommentService {
    void addComment(CommentServiceModel commentServiceModel, String user, OfferServiceModel offerServiceModel);
}
