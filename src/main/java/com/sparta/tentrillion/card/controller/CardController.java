package com.sparta.tentrillion.card.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.tentrillion.aop.Envelop;
import com.sparta.tentrillion.card.dto.requestdto.CardRequestDto;
import com.sparta.tentrillion.card.dto.responsedto.CardResponseDto;
import com.sparta.tentrillion.card.service.CardService;
import com.sparta.tentrillion.global.argumentResolver.annotation.LoginUser;
import com.sparta.tentrillion.stat.repository.StatRepository;
import com.sparta.tentrillion.user.User;
import com.sparta.tentrillion.user.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;
    private final UserRepository userRepository;
    private final StatRepository statRepository;

    // 카드 생성
    @Envelop("카드가 생성되었습니다.")
    @PostMapping("/{statId}")
    public ResponseEntity<CardResponseDto> createCard(@Valid @RequestBody CardRequestDto cardRequestDto,
                                           @PathVariable Long statId,
                                           @LoginUser User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cardService.createCard(cardRequestDto, statId, user));
    }

    // 카드 수정
    @Envelop("카드가 수정되었습니다.")
    @PutMapping("/{cardId}")
    public ResponseEntity<CardResponseDto> updateCard(@Valid @RequestBody CardRequestDto requestDto,
                                                      @PathVariable Long cardId) throws JsonProcessingException {

        CardResponseDto cardResponseDto = cardService.updateCard(requestDto, cardId);

        return new ResponseEntity<>(cardResponseDto, HttpStatus.OK);
    }

    // 카드 삭제
    @Envelop("카드가 삭제되었습니다.")
    @DeleteMapping("/{cardId}")
    public ResponseEntity<?> deleteCard(@PathVariable Long cardId) {

        cardService.deleteCard(cardId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 카드 전체 조회
    @Envelop("카드 전체가 조회되었습니다.")
    @GetMapping
    public ResponseEntity<List<CardResponseDto>> getAllCards() {
        List<CardResponseDto> cardResponseDtoList = cardService.getAllCards();

        return new ResponseEntity<>(cardResponseDtoList, HttpStatus.OK);
    }

    // 작업자별 카드 조회
    @Envelop("작업자별 카드가 조회되었습니다.")
    @GetMapping("/creators/{creatorId}")
    public ResponseEntity<List<CardResponseDto>> getCardsByCreatorId(@PathVariable long creatorId) {
        List<CardResponseDto> cardscreatorIdList = cardService.getCardsByCreatorId(creatorId);

        return new ResponseEntity<>(cardscreatorIdList, HttpStatus.OK);
    }

    // 컬럼별 카드 조회
    @Envelop("컬럼별 카드가 조회되었습니다.")
    @GetMapping("/stats/{statId}")
    public ResponseEntity<List<CardResponseDto>> getCardsByStatId(@PathVariable Long statId) {
        List<CardResponseDto> cardsAssigneeIdList = cardService.getCardsByStatId(statId);

        return new ResponseEntity<>(cardsAssigneeIdList, HttpStatus.OK);
    }
}