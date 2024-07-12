package com.sparta.tentrillion.board.controller;

import com.sparta.tentrillion.board.dto.request.BoardRequestDto;
import com.sparta.tentrillion.board.dto.request.InviteBoardRequestDto;
import com.sparta.tentrillion.board.dto.response.BoardResponseDto;
import com.sparta.tentrillion.board.entity.Board;
import com.sparta.tentrillion.board.service.BoardService;
import com.sparta.tentrillion.global.exception.BusinessException;
import com.sparta.tentrillion.user.User;
import com.sparta.tentrillion.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import  com.sparta.tentrillion.security.principal.UserPrincipal;

import java.util.List;

import static com.sparta.tentrillion.global.exception.ErrorCode.UNAUTHORIZED_USER;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final UserService userService;

    // TODO 생성 POST
    @PostMapping
    public ResponseEntity<BoardResponseDto> createBoard(@RequestBody @Valid BoardRequestDto requestDto,
                                                        @RequestHeader(value = "RefreshToken") String refreshToken,
                                                        @AuthenticationPrincipal UserPrincipal userDetails) {
        // TODO 리프레시 토큰으로 유저 조회
        // 없으면 에러 반환
        // 시큐리티 인증객체의 값과도 비교
        User user = userService.findUserByRefreshToken(refreshToken);
        if (!user.equals(userDetails.getUser())) {
            throw new BusinessException(UNAUTHORIZED_USER);
        }

        // TODO Manager가 아닌데 생성을 시도하는 경우 : user.getRole == "USER"
        if (User.Role.USER.equals(user.getRole())) {
            throw new BusinessException(UNAUTHORIZED_USER);
        }

        // TODO 응답 : "msg" : “보드 생성 완료” “status” : 201
        //              result
        BoardResponseDto responseDto = boardService.createBoard(requestDto,user);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);

    }

    // TODO 수정 PUT
    @PutMapping("/{boardId}")
    public ResponseEntity<BoardResponseDto> updateBoard(@PathVariable long boardId,
                                                        @RequestBody @Valid BoardRequestDto requestDto,
                                                        @RequestHeader(value = "RefreshToken") String refreshToken,
                                                        @AuthenticationPrincipal UserPrincipal userDetails) {

        // TODO 리프레시 토큰으로 유저 조회
        User user = userService.findUserByRefreshToken(refreshToken);
        if (!user.equals(userDetails.getUser())) {
            throw new BusinessException(UNAUTHORIZED_USER);
        }

        // TODO Manager가 아닌데 생성을 시도하는 경우 : user.getRole == "USER"
        if (User.Role.USER.equals(user.getRole())) {
            throw new BusinessException(UNAUTHORIZED_USER);
        }

        // TODO 초대받은 사용자인지 확인
        Board board = boardService.findBoardById(boardId);
        boardService.checkSenderInvited(user, board);

        // TODO response "msg" : “보드 수정 완료”  “status” : 200
//          result
        BoardResponseDto responseDto = boardService.updateBoard(boardId, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }


    // TODO 삭제 DELETE
    @DeleteMapping("/{boardId}")
    public ResponseEntity deleteBoard(@PathVariable long boardId,
                                      @RequestHeader(value = "RefreshToken") String refreshToken,
                                      @AuthenticationPrincipal UserPrincipal userDetails) {

        // TODO 리프레시 토큰으로 유저 조회
        User user = userService.findUserByRefreshToken(refreshToken);
        if (!user.equals(userDetails.getUser())) {
            throw new BusinessException(UNAUTHORIZED_USER);
        }

        // TODO Manager가 아닌데 생성을 시도하는 경우 : user.getRole == "USER"
        if (User.Role.USER.equals(user.getRole())) {
            throw new BusinessException(UNAUTHORIZED_USER);
        }

        // TODO 초대받은 사용자인지 확인
        Board board = boardService.findBoardById(boardId);
        boardService.checkSenderInvited(user, board);

        // TODO response "msg" : “보드 삭제 완료” "status": 204
        boardService.deleteBoard(boardId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // TODO 초대 POST
    // TODO 어느 보드(boardId)에 누가(senderId) 누구(receiverId)를 초대함.
    @PostMapping("/{boardId")
    public ResponseEntity inviteToBoard(@PathVariable long boardId,
                                                          @RequestBody @Valid InviteBoardRequestDto requestDto,
                                                          @RequestHeader(value = "RefreshToken") String refreshToken,
                                                          @AuthenticationPrincipal UserPrincipal userDetails) {
        // senderId
        // TODO 가입되지 않은 사용자 user.findByRefreshToken == null
        User sender = userService.findUserByRefreshToken(refreshToken);

        // TODO Manager 가 아닌데 초대: access token으로부터 user.getRole != "Manager"
        if (User.Role.USER.equals(sender.getRole())) {
            throw new BusinessException(UNAUTHORIZED_USER);
        }

        // TODO 초대받은 사용자인지 확인
        Board board = boardService.findBoardById(boardId);
        boardService.checkSenderInvited(sender, board);

        // TODO response : "msg" : “보드 초대 완료”, “status” : 200
        boardService.inviteToBoard(boardId, requestDto, sender);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // TODO 목록조회 GET : 페이징X
    // 내가 볼 수 있는 보드만 보여야 함.
    @GetMapping
    public ResponseEntity<List<BoardResponseDto>> getAllBoards(@RequestHeader(value = "RefreshToken") String refreshToken,
                                                               @AuthenticationPrincipal UserPrincipal userDetails) {
        // senderId
        // TODO 가입되지 않은 사용자 user.findByRefreshToken == null
        User user = userService.findUserByRefreshToken(refreshToken);

        // TODO ”msg”: “보드 목록 조회 완료” “status” : 200
        //      result
        List<BoardResponseDto> responseDtoList = boardService.getAllInvitedBoards(user);
        return ResponseEntity.status(HttpStatus.OK).body(responseDtoList);
    }

}
