package com.sparta.tentrillion.stat.controller;

import com.sparta.tentrillion.aop.Envelop;
import com.sparta.tentrillion.global.argumentResolver.annotation.LoginUser;
import com.sparta.tentrillion.stat.dto.StatRequestDto;
import com.sparta.tentrillion.stat.dto.StatResponseDto;
import com.sparta.tentrillion.stat.service.StatService;
import com.sparta.tentrillion.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class StatController {

    private final StatService statService;

    // stat 생성
    @Envelop("컬럼 생성입니다.")
    @PostMapping("/{boardId}/stats")
    public ResponseEntity<StatResponseDto> creatStat(@Valid @RequestBody StatRequestDto statRequestDto,
                                                     @PathVariable(value = "boardId") Long boardId
            ,
                                                     @LoginUser User user
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(statService.createStat(statRequestDto, user, boardId));
    }

    // stat 수정
    @Envelop("컬럼 수정입니다.")
    @PutMapping("/{boardId}/stats/{statId}")
    public ResponseEntity<StatResponseDto> updateStat(@Valid @RequestBody StatRequestDto statRequestDto,
                                                      @PathVariable(value = "boardId") Long boardId,
                                                      @PathVariable(value = "statId") Long statId,
                                                      @LoginUser User user) {
        return ResponseEntity.ok().body(statService.updateStat(statRequestDto, boardId, user, statId));
    }

    // stat 삭제
    @Envelop("컬럼 삭제입니다.")
    @DeleteMapping("/{boardId}/stats/{statId}")
    public ResponseEntity<StatResponseDto> deleteStat(@PathVariable(value = "boardId") Long boardId,
                                                      @PathVariable(value = "statId") Long statId,
                                                      @LoginUser User user) {
        return ResponseEntity.ok().body(statService.deleteStat(boardId, statId, user));
    }
    // stat 순서 변경


}