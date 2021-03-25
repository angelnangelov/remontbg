package com.angelangelov.remont_bg.unit;

import com.angelangelov.remont_bg.error.comment.CommentWithIdNotExists;
import com.angelangelov.remont_bg.model.entities.Comment;
import com.angelangelov.remont_bg.model.entities.Offer;
import com.angelangelov.remont_bg.model.services.CommentServiceModel;
import com.angelangelov.remont_bg.model.services.OfferServiceModel;
import com.angelangelov.remont_bg.repository.CommentRepository;
import com.angelangelov.remont_bg.service.OfferService;
import com.angelangelov.remont_bg.service.UserService;
import com.angelangelov.remont_bg.service.impl.CommentServiceImpl;
import com.angelangelov.remont_bg.service.impl.OfferServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CommentServiceTests {
    @InjectMocks
    CommentServiceImpl commentService;
    @Mock
    private  ModelMapper modelMapper;
    @Mock
    private  CommentRepository commentRepository;

    @Mock
    private  UserService userService;
    Comment comment;
    @Before
    public void initTests() {
        comment = new Comment();
    }

    @Test(expected = CommentWithIdNotExists.class)
    public void findCommentById_whenCommentIdNotExists_throws() {
        Mockito.when(commentService.findCommentById("notRightId"))
                .thenThrow(new CommentWithIdNotExists());
        commentService.findCommentById("notRightId");
    }



}
