package com.sparta.tentrillion.card.service;

import com.sparta.tentrillion.card.dto.requestdto.CreateCardRequestDto;
import com.sparta.tentrillion.card.dto.responsedto.CreateCardResponseDto;
import com.sparta.tentrillion.card.entity.Card;
import com.sparta.tentrillion.card.repository.CardRepository;
import com.sparta.tentrillion.global.exception.BusinessException;
import com.sparta.tentrillion.global.exception.ErrorCode;
import com.sparta.tentrillion.stat.Stat;
import com.sparta.tentrillion.stat.StatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {

    private final CardRepository cardRepository;
    private final StatRepository statRepository;
    private final

    public CardService(CardRepository cardRepository, StatRepository statRepository) {
        this.cardRepository = cardRepository;
        this.statRepository = statRepository;
    }

    public CreateCardResponseDto createCard(CreateCardRequestDto createCardRequestDto, Long statId) {
        if (createCardRequestDto.getTitle() == null || createCardRequestDto.getDescription() == null) {
            throw new IllegalArgumentException("Title or Description are required");
        }

        Stat stat = statRepository.findById(statId).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND)
        );


        Card card = card.builder()
                .title(createCardRequestDto.getTitle())
                .description(createCardRequestDto.getDescription())
                .dueDate(createCardRequestDto.getDueDate())
                .stat(stat)
                .assignee()
                .build();
        return CreateCardResponseDto.
    }

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    public List<Card> getCardsByAssignee(String assignee) {
        return cardRepository.findByAssignee(assignee);
    }

    public List<Card> getCardsByStats(Stat stat) {
        return cardRepository.findByStats(stat);
    }
}
