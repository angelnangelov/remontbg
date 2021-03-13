package com.angelangelov.remont_bg.service.impl;

import com.angelangelov.remont_bg.model.entities.Comment;
import com.angelangelov.remont_bg.model.entities.Offer;
import com.angelangelov.remont_bg.model.entities.User;
import com.angelangelov.remont_bg.model.services.CommentServiceModel;
import com.angelangelov.remont_bg.model.services.OfferServiceModel;
import com.angelangelov.remont_bg.repository.CommentRepository;
import com.angelangelov.remont_bg.service.CommentService;
import com.angelangelov.remont_bg.service.OfferService;
import com.angelangelov.remont_bg.service.ToolOfferService;
import com.angelangelov.remont_bg.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    private final ModelMapper modelMapper;
    private final CommentRepository commentRepository;

    private final UserService userService;

    public CommentServiceImpl(ModelMapper modelMapper, CommentRepository commentRepository, OfferService offerService, ToolOfferService toolOfferService, UserService userService) {
        this.modelMapper = modelMapper;
        this.commentRepository = commentRepository;

        this.userService = userService;
    }

    @Override
    public void addComment(CommentServiceModel commentServiceModel, String user, OfferServiceModel offerServiceModel) {
        Comment comment = modelMapper.map(commentServiceModel, Comment.class);
        comment.setUser(modelMapper.map(userService.findUserByUsername(user), User.class));
        comment.setOffer(modelMapper.map(offerServiceModel, Offer.class));
        commentRepository.save(comment);



    }
}
