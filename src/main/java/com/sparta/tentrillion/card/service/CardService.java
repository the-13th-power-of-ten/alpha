package com.sparta.tentrillion.card.service;

import com.sparta.tentrillion.card.dto.request.CardRequestDto;
import com.sparta.tentrillion.card.dto.response.CardResponseDto;
import com.sparta.tentrillion.card.entity.Card;
import com.sparta.tentrillion.card.repository.CardRepository;
import com.sparta.tentrillion.global.exception.BusinessException;
import com.sparta.tentrillion.global.exception.ErrorCode;
import com.sparta.tentrillion.stat.entity.Stat;
import com.sparta.tentrillion.stat.repository.StatRepository;
import com.sparta.tentrillion.user.entity.User;
import com.sparta.tentrillion.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardService {

    private final CardRepository cardRepository;
    private final StatRepository statRepository;
    private final UserRepository userRepository;

    public CardService(CardRepository cardRepository, StatRepository statRepository, UserRepository userRepository) {
        this.cardRepository = cardRepository;
        this.statRepository = statRepository;
        this.userRepository = userRepository;
    }

    // 카드 생성
    @Transactional
    public CardResponseDto createCard(CardRequestDto cardRequestDto, Long statId, User loginUser) {
        // 현재 로그인된 사용자 찾기
        User currentUser = userRepository.findByUsername(loginUser.getUsername()).orElseThrow(
                () -> new BusinessException(ErrorCode.USER_NOT_FOUND)
        );

        Stat stat = statRepository.findById(statId).orElseThrow(
                () -> new BusinessException(ErrorCode.BAD_REQUEST)
        );

        Card card = Card.builder()
                .title(cardRequestDto.getTitle())
                .description(cardRequestDto.getDescription())
                .dueDate(cardRequestDto.getDueDate())
                .creator(currentUser)
                .stat(stat)
                .build();

        card = cardRepository.save(card);

        CardResponseDto cardResponseDto = new CardResponseDto(card);

        return cardResponseDto;
    }


    // 카드 수정
    @Transactional
    public CardResponseDto updateCard(CardRequestDto requestDto, Long cardId) {
        Card card = cardRepository.findById(cardId).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND)
        );

        card.updateCard(requestDto);

        return new CardResponseDto(card);
    }

    // 카드 삭제
    public void deleteCard(Long cardId) {
        Card card = cardRepository.findById(cardId).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND)
        );

        cardRepository.delete(card);
    }

    private Card createCardForUser(CardRequestDto cardRequestDto, Stat stat, User creator) {
        Card card = Card.builder()
                .title(cardRequestDto.getTitle())
                .description(cardRequestDto.getDescription())
                .dueDate(cardRequestDto.getDueDate())
                .creator(creator)
                .stat(stat)
                .build();

        card = cardRepository.save(card);

        return card;
    }

     // 카드 전체 조회
    public List<CardResponseDto> getAllCards() {
        List<Card> cards = cardRepository.findAll();

        return cards.stream().map(CardResponseDto::new).collect(Collectors.toList());
    }

    // 작업자별 카드 조회
    public List<CardResponseDto> getCardsByCreatorId(long creatorId) {
        List<Card> cards = cardRepository.findByCreatorId(creatorId);

        return cards.stream().map(CardResponseDto::new).collect(Collectors.toList());
    }

    // 컬럼별 카드 조회
    public List<CardResponseDto> getCardsByStatId(Long statId) {
        List<Card> cards = cardRepository.findByStatId(statId);

        return cards.stream().map(CardResponseDto::new).collect(Collectors.toList());
    }
}