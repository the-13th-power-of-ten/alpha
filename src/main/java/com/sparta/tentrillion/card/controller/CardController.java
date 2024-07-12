package com.sparta.tentrillion.card.controller;

import com.sparta.tentrillion.card.dto.requestdto.CreateCardRequestDto;
import com.sparta.tentrillion.card.dto.responsedto.CreateCardResponseDto;
import com.sparta.tentrillion.card.entity.Card;
import com.sparta.tentrillion.card.service.CardService;
import com.sparta.tentrillion.stat.Stat;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    // 카드 생성
    @PostMapping("/{statId}")
    public ResponseEntity<?> createCard(@Valid @RequestBody CreateCardRequestDto createCardRequestDto,
                                        @PathVariable Long statId) {

        CreateCardResponseDto createCardResponseDto = cardService.createCard(createCardRequestDto, statId);

        return new ResponseEntity<> (createCardResponseDto, HttpStatus.CREATED);
    }

    // 카드 전체 조회
    @GetMapping
    public List<Card> getAllCards() {
        return cardService.getAllCards().stream()
                .map()
                .collect(Collectors.toList());
    }

    // 작업자별 카드 조회
    @GetMapping("/assignee/{assigneeId}")
    public List<Card> getCardsByAssigneeId(@PathVariable String assigneeId) {
        return cardService.getCardsByAssignee(assigneeId);
    }

    // 상태별 조회
    @GetMapping("/stats/{statId}")
    public List<Card> getCardsByStatId(@PathVariable Stat statId) {
        List<Card> getCardsByStatIdList = cardService.getCardsByStatId(statId);

        return
    }


    // 카드 수정


    // 카드 삭제
}
