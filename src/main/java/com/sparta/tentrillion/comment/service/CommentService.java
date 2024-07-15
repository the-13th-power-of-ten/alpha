package com.sparta.tentrillion.comment.service;

import com.sparta.tentrillion.card.Card;
import com.sparta.tentrillion.card.CardRepository;
import com.sparta.tentrillion.comment.dto.CommentRequestDto;
import com.sparta.tentrillion.comment.dto.CommentResponseDto;
import com.sparta.tentrillion.comment.entity.Comment;
import com.sparta.tentrillion.comment.repository.CommentRepository;
import com.sparta.tentrillion.global.exception.BusinessException;
import com.sparta.tentrillion.global.exception.ErrorCode;
import com.sparta.tentrillion.user.entity.User;
import com.sparta.tentrillion.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final CardRepository cardRepository;
    private final UserService userService;

    public CommentResponseDto createComment(Long cardId, CommentRequestDto commentRequestDto, String username) {
        //user 찾기
        User user = userService.findByUsername(username);
        //card 찾기
        Card card = cardRepository.findById(cardId).orElseThrow(
                ()-> new BusinessException(ErrorCode.NOT_FOUND)
        );
        Comment comment = Comment.builder()
                .description(commentRequestDto.getComment())
                .user(user)
                .card(card)
                .build();

        return new CommentResponseDto(commentRepository.save(comment));
    }

    public List<CommentResponseDto> getComment(Long cardId) {
        Card card = cardRepository.findById(cardId).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND)
        );
        List<Comment> commentList = commentRepository.findByCardId(card.getId());
        return commentList.stream().map(CommentResponseDto::new)
                .toList();
    }
}
