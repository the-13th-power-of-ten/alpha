package com.sparta.tentrillion.card.controller;

import com.sparta.tentrillion.card.dto.requestdto.CreateCardRequestDto;
import com.sparta.tentrillion.card.dto.requestdto.UpdateCardRequestDto;
import com.sparta.tentrillion.card.dto.responsedto.CardResponseDto;
import com.sparta.tentrillion.card.dto.responsedto.CreateCardResponseDto;
import com.sparta.tentrillion.card.dto.responsedto.UpdateCardResponseDto;
import com.sparta.tentrillion.card.entity.Card;
import com.sparta.tentrillion.card.service.CardService;
import com.sparta.tentrillion.stat.Stat;
import com.sparta.tentrillion.user.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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

        return new ResponseEntity<>(createCardResponseDto, HttpStatus.CREATED);
    }
}