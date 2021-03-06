package com.angelangelov.remont_bg.repository;

import com.angelangelov.remont_bg.model.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository  extends JpaRepository<Comment,String> {
    List<Comment> findAllByOfferIdOrderByPostedTime(String id);
}
