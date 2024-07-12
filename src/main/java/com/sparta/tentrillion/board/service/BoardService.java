package com.sparta.tentrillion.board.service;

import com.sparta.tentrillion.board.repository.BoardQRepository;
import com.sparta.tentrillion.board.repository.BoardRepository;
import com.sparta.tentrillion.board.repository.InvitedBoardRepository;
import com.sparta.tentrillion.board.dto.request.BoardRequestDto;
import com.sparta.tentrillion.board.dto.request.InviteBoardRequestDto;
import com.sparta.tentrillion.board.dto.response.BoardResponseDto;
import com.sparta.tentrillion.board.entity.Board;
import com.sparta.tentrillion.board.entity.InvitedBoard;
import com.sparta.tentrillion.global.exception.BusinessException;
import com.sparta.tentrillion.user.User;
import com.sparta.tentrillion.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.sparta.tentrillion.global.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final InvitedBoardRepository invitedBoardRepository;
    private final BoardQRepository boardQRepository;
    private final UserService userService;

    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto requestDto, User user) {
        Board board = new Board(requestDto, user);
        boardRepository.save(board);
        return new BoardResponseDto(board);
    }

    @Transactional
    public BoardResponseDto updateBoard(long boardId, BoardRequestDto requestDto) {
        // TODO 보드가 없는 경우
        Board board = findBoardById(boardId);

        board.update(requestDto);
        boardRepository.save(board);
        return new BoardResponseDto(board);
    }

    @Transactional
    public void deleteBoard(long boardId) {
        // TODO 이미 삭제된 보드인 경우 = 없는 보드인 경우 : boardRepo.findById(boardId) == null
        Board board = findBoardById(boardId);

        boardRepository.delete(board);
    }

    // TODO 어느 보드(boardId)에 누가(senderId) 누구(receiverId)를 초대함.
    @Transactional
    public void inviteToBoard(long boardId, InviteBoardRequestDto requestDto, User sender) {
        // TODO 없는 보드 boardRepo.findById(barodId) == null
        Board board = findBoardById(boardId);

        // receiverId
        // TODO 초대받은 사람이 가입자인지 확인 user.findByEmail == null. requestDto
        User receiver = userService.findUserByUsername(requestDto.getUsername());

        // TODO Manager 지만 본인이 생성하지 않은 보드
        //      board.getUser.getId != senderId
        if (board.getUser().getId() != sender.getId()) {
            throw new BusinessException(UNAUTHORIZED_USER);
        }

        // TODO Manager 지만 초대되지 않은 보드에 초대
        //      invitedBoard에 저장된 보드,리시버 = 보드아이디 senderId 조합이 있으면 초대된 것.
        checkSenderInvited(sender, board);

        // TODO 이미 해당 보드에 초대된 사용자 invitedBoards.getReceiver.getid == receiverId
        // sender가 이미 receiver를 board에 초대한 적 있는지 조회.
        checkReceiverInvited(sender, receiver, board);

        InvitedBoard invitedBoard = new InvitedBoard(sender, receiver, board);
        invitedBoardRepository.save(invitedBoard);

    }

    @Transactional(readOnly = true)
    public List<BoardResponseDto> getAllInvitedBoards(User user) {
        // TODO 초대받은 보드만 조회
        List<Board> boardList = boardQRepository.findAllInvitedBoards(user);
        return boardList.stream().map(BoardResponseDto::new).toList();
    }

    public void checkSenderInvited(User sender, Board board) {
        InvitedBoard invitedBoard = invitedBoardRepository.findByBoardAndReceiver(board, sender);
        if (invitedBoard == null) {
            throw new BusinessException(BOARD_NOT_INVITED);
        }
    }

    public void checkReceiverInvited(User sender, User receiver, Board board) {
        InvitedBoard invitedBoard = invitedBoardRepository.findByBoardAndSenderAndReceiver(board, sender, receiver);
        if (invitedBoard != null) {
            throw new BusinessException(BOARD_ALREADY_INVITED);
        }
    }

    public Board findBoardById(long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(
                        () -> new BusinessException(BOARD_NOT_FOUND)
                );
    }

}
