package com.angelangelov.remont_bg.unit;

import com.angelangelov.remont_bg.error.comment.CommentWithIdNotExists;
import com.angelangelov.remont_bg.model.entities.Comment;
import com.angelangelov.remont_bg.model.entities.Offer;
import com.angelangelov.remont_bg.model.entities.Role;
import com.angelangelov.remont_bg.model.entities.User;
import com.angelangelov.remont_bg.model.services.CommentServiceModel;
import com.angelangelov.remont_bg.model.services.OfferServiceModel;
import com.angelangelov.remont_bg.model.services.UserServiceModel;
import com.angelangelov.remont_bg.repository.CommentRepository;
import com.angelangelov.remont_bg.service.CommentService;
import com.angelangelov.remont_bg.service.OfferService;
import com.angelangelov.remont_bg.service.UserService;
import com.angelangelov.remont_bg.service.impl.CommentServiceImpl;
import com.angelangelov.remont_bg.service.impl.OfferServiceImpl;
import com.angelangelov.remont_bg.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class CommentServiceTests {
    @Mock
    private  CommentRepository mockCommentRepository;

    @Mock
    private  UserService mockUserService;

    private CommentService commentService;

    @Before
    public void initTests() {
        commentService = new CommentServiceImpl(
                new ModelMapper(),
                mockCommentRepository,
                mockUserService
        );
    }

    @Test
    public void findCommentById_WhenCommentExists_ShouldWork() {
    Comment testComment = new Comment();
    testComment.setId("testId");
    testComment.setUser(new User());
    testComment.setOffer(new Offer());
    testComment.setDescription("TEST");
    testComment.setPostedTime(LocalDateTime.now());
        Mockito.when(mockCommentRepository.findById("testId"))
                .thenReturn(Optional.of(testComment));
        CommentServiceModel resultComment = commentService.findCommentById("testId");
        Assert.assertEquals(resultComment.getDescription(),testComment.getDescription());
        Assert.assertEquals(resultComment.getPostedTime(),testComment.getPostedTime());

    }



    @Test(expected = CommentWithIdNotExists.class)
    public void findCommentById_whenCommentIdNotExists_throws() {
        Mockito.when(commentService.findCommentById("notRightId"))
                .thenThrow(new CommentWithIdNotExists());
        commentService.findCommentById("notRightId");
    }



}
