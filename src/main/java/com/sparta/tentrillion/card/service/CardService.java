package com.sparta.tentrillion.card.service;

import com.sparta.tentrillion.card.dto.requestdto.CreateCardRequestDto;
import com.sparta.tentrillion.card.dto.responsedto.CreateCardResponseDto;
import com.sparta.tentrillion.card.entity.Card;
import com.sparta.tentrillion.card.repository.CardRepository;
import com.sparta.tentrillion.global.exception.BusinessException;
import com.sparta.tentrillion.global.exception.ErrorCode;
import com.sparta.tentrillion.stat.Stat;
import org.springframework.stereotype.Service;

@Service
public class CardService {

    private final CardRepository cardRepository;
    private final StatRepository statRepository;

    public CardService(CardRepository cardRepository, StatRepository statRepository) {
        this.cardRepository = cardRepository;
        this.statRepository = statRepository;
    }

    // 카드 생성
    public CreateCardResponseDto createCard(CreateCardRequestDto createCardRequestDto, Long statId) {
        if (createCardRequestDto.getTitle() == null || createCardRequestDto.getDescription() == null) {
            throw new BusinessException(ErrorCode.INPUT_TITLE_DESCRIPTION);
        }

        Stat stat = statRepository.findById(statId).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND)
        );

        Card card = Card.builder()
                .title(createCardRequestDto.getTitle())
                .description(createCardRequestDto.getDescription())
                .dueDate(createCardRequestDto.getDueDate())
                .stat(stat)
//                .assignee(user) -> UserDetailsImpl 에서 가져오기
                .build();

        cardRepository.save(card);

        return new CreateCardResponseDto(card);
    }
}