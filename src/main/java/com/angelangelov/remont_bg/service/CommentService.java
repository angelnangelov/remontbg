package com.angelangelov.remont_bg.service;

import com.angelangelov.remont_bg.model.entities.Comment;
import com.angelangelov.remont_bg.model.services.CommentServiceModel;
import com.angelangelov.remont_bg.model.services.OfferServiceModel;

import java.util.List;

public interface CommentService {
    CommentServiceModel addComment(CommentServiceModel commentServiceModel, String user, OfferServiceModel offerServiceModel);

    List<CommentServiceModel> findCommentsByOfferId(String id);

    void deleteComment(String id);
}
