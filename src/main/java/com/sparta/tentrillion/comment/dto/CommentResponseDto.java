package com.sparta.tentrillion.comment.dto;

import com.sparta.tentrillion.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private final Long commentId;
    private final String comment;
    private final LocalDateTime createdAt;

    public CommentResponseDto(Comment comment){
        this.commentId = comment.getId();
        this.comment = comment.getDescription();
        this.createdAt =comment.getCreatedAt();
    }
}
