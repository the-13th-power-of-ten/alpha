package com.sparta.tentrillion.stat.service;

import com.sparta.tentrillion.board.entity.Board;
import com.sparta.tentrillion.board.service.BoardService;
import com.sparta.tentrillion.global.exception.BusinessException;
import com.sparta.tentrillion.global.exception.ErrorCode;
import com.sparta.tentrillion.stat.dto.StatRequestDto;
import com.sparta.tentrillion.stat.dto.StatResponseDto;
import com.sparta.tentrillion.stat.entity.Stat;
import com.sparta.tentrillion.stat.repository.StatRepository;
import com.sparta.tentrillion.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatService {

    private final StatRepository statRepository;
    private final BoardService boardService;

    @Transactional
    public StatResponseDto createStat(StatRequestDto statRequestDto, User user, Long boardId) {
        if (!user.getRole().equals(User.Role.MANAGER)) {
            throw new BusinessException(ErrorCode.NOT_ADMIN);
        }
        // board 찾기
        Board board = boardService.findBoardById(boardId);
        // stat 생성
        Stat stat = Stat.builder()
                .title(statRequestDto.getTitle())
                .board(board)
                .build();

        Stat saveStat = statRepository.save(stat);

        return new StatResponseDto(saveStat);
    }

    @Transactional
    public StatResponseDto updateStat(StatRequestDto statRequestDto, Long boardId, User user, Long statId) {
        if (!user.getRole().equals(User.Role.MANAGER)) {
            throw new BusinessException(ErrorCode.NOT_ADMIN);
        }
        //board 찾기
        Board board = boardService.findBoardById(boardId);
        //stat 찾기
        Stat stat = statRepository.findByBoardIdAndStatId(board, statId).orElseThrow(() ->
                new BusinessException(ErrorCode.NOT_FOUND));
        //찾은 stat update
        stat.updateStat(statRequestDto.getTitle());
        return new StatResponseDto(stat);
    }

    @Transactional
    public StatResponseDto deleteStat(Long boardId, Long statId, User user) {
        if (!user.getRole().equals(User.Role.MANAGER)) {
            throw new BusinessException(ErrorCode.NOT_ADMIN);
        }
        //board 찾기
        Board board = boardService.findBoardById(boardId);
        // 해당 stat 찾기
        Stat stat = statRepository.findById(statId).orElseThrow(() ->
                new BusinessException(ErrorCode.NOT_FOUND));
        statRepository.delete(stat);
        return new StatResponseDto(stat);
    }
}
