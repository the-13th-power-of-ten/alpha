package com.sparta.tentrillion.comment.repository;

import com.sparta.tentrillion.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository  extends JpaRepository<Comment,Long> {

    List<Comment> findByCardId(Long id);
}
