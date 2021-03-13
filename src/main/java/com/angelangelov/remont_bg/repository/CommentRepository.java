package com.angelangelov.remont_bg.repository;

import com.angelangelov.remont_bg.model.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository  extends JpaRepository<Comment,String> {
}
