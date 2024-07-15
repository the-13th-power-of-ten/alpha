package com.sparta.tentrillion.board.controller;

import com.sparta.tentrillion.aop.Envelop;
import com.sparta.tentrillion.board.dto.request.BoardRequestDto;
import com.sparta.tentrillion.board.dto.request.InviteBoardRequestDto;
import com.sparta.tentrillion.board.dto.response.BoardResponseDto;
import com.sparta.tentrillion.board.service.BoardService;
import com.sparta.tentrillion.global.argumentResolver.annotation.LoginUser;
import com.sparta.tentrillion.global.exception.BusinessException;
import com.sparta.tentrillion.user.entity.User;
import com.sparta.tentrillion.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sparta.tentrillion.global.exception.ErrorCode.UNAUTHORIZED_USER;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final UserService userService;

    @Envelop("보드 생성 완료")
    @PostMapping
    public ResponseEntity<BoardResponseDto> createBoard(@RequestBody @Valid BoardRequestDto requestDto,
                                                        @RequestHeader(value = "RefreshToken") String refreshToken,
                                                        @LoginUser User loginUser) {
        User user = userService.findByRefreshToken(refreshToken);
        if (!user.getUsername().equals(loginUser.getUsername())) {
            throw new BusinessException(UNAUTHORIZED_USER);
        }

        if (User.Role.USER.equals(user.getRole())) {
            throw new BusinessException(UNAUTHORIZED_USER);
        }

        BoardResponseDto responseDto = boardService.createBoard(requestDto, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @Envelop("보드 수정 완료")
    @PutMapping("/{boardId}")
    public ResponseEntity<BoardResponseDto> updateBoard(@PathVariable long boardId,
                                                        @RequestBody @Valid BoardRequestDto requestDto,
                                                        @RequestHeader(value = "RefreshToken") String refreshToken,
                                                        @LoginUser User loginUser) {
        User user = userService.findByRefreshToken(refreshToken);
        if (!user.getUsername().equals(loginUser.getUsername())) {
            throw new BusinessException(UNAUTHORIZED_USER);
        }

        if (User.Role.USER.equals(user.getRole())) {
            throw new BusinessException(UNAUTHORIZED_USER);
        }

        BoardResponseDto responseDto = boardService.updateBoard(boardId, requestDto, user);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @Envelop("보드 삭제 완료")
    @DeleteMapping("/{boardId}")
    public ResponseEntity deleteBoard(@PathVariable long boardId,
                                      @RequestHeader(value = "RefreshToken") String refreshToken,
                                      @LoginUser User loginUser) {
        User user = userService.findByRefreshToken(refreshToken);
        if (!user.getUsername().equals(loginUser.getUsername())) {
            throw new BusinessException(UNAUTHORIZED_USER);
        }

        if (User.Role.USER.equals(user.getRole())) {
            throw new BusinessException(UNAUTHORIZED_USER);
        }

        boardService.deleteBoard(boardId, user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Envelop("보드 초대 완료")
    @PostMapping("/{boardId}")
    public ResponseEntity inviteToBoard(@PathVariable long boardId,
                                        @RequestBody @Valid InviteBoardRequestDto requestDto,
                                        @RequestHeader(value = "RefreshToken") String refreshToken,
                                        @LoginUser User loginUser) {
        User sender = userService.findByRefreshToken(refreshToken);
        if (!sender.getUsername().equals(loginUser.getUsername())) {
            throw new BusinessException(UNAUTHORIZED_USER);
        }

        if (User.Role.USER.equals(sender.getRole())) {
            throw new BusinessException(UNAUTHORIZED_USER);
        }

        boardService.inviteToBoard(boardId, requestDto, sender);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Envelop("보드 목록 조회 완료")
    @GetMapping
    public ResponseEntity<List<BoardResponseDto>> getAllBoards(@RequestHeader(value = "RefreshToken") String refreshToken,
                                                               @LoginUser User loginUser) {
        User user = userService.findByRefreshToken(refreshToken);
        if (!user.getUsername().equals(loginUser.getUsername())) {
            throw new BusinessException(UNAUTHORIZED_USER);
        }

        List<BoardResponseDto> responseDtoList = boardService.getAllInvitedBoards(user);
        return ResponseEntity.status(HttpStatus.OK).body(responseDtoList);
    }
}
