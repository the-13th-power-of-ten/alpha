package com.sparta.tentrillion.card.dto.responsedto;

import com.sparta.tentrillion.card.entity.Card;
import com.sparta.tentrillion.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardResponseDto {

    private Long id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private long creatorId;


    // Card Entity 이용해 CardResponseDto 객체를 생성하는 생성자
    public CardResponseDto(Card card) {
        this.id = card.getId();
        this.title = card.getTitle();
        this.description = card.getDescription();
        this.dueDate = card.getDueDate();
        this.createdAt = card.getCreatedAt();
        this.modifiedAt = card.getModifiedAt();
        this.creatorId = card.getCreator().getId();
    }
}
