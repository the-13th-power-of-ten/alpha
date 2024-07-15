package com.sparta.tentrillion.board.service;

import com.sparta.tentrillion.board.dto.request.BoardRequestDto;
import com.sparta.tentrillion.board.dto.request.InviteBoardRequestDto;
import com.sparta.tentrillion.board.dto.response.BoardResponseDto;
import com.sparta.tentrillion.board.entity.Board;
import com.sparta.tentrillion.board.entity.InvitedBoard;
import com.sparta.tentrillion.board.repository.BoardRepository;
import com.sparta.tentrillion.board.repository.InvitedBoardRepository;
import com.sparta.tentrillion.global.exception.BusinessException;
import com.sparta.tentrillion.user.entity.User;
import com.sparta.tentrillion.user.service.UserService;
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
    private final UserService userService;

    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto requestDto, User user) {
        Board board = new Board(requestDto, user);
        boardRepository.save(board);
        return new BoardResponseDto(board);
    }

    @Transactional
    public BoardResponseDto updateBoard(long boardId, BoardRequestDto requestDto, User user) {
        Board board = findBoardById(boardId);

        if (board.getUser().getId() == user.getId() || checkSenderInvited(user, board)) {
            board.update(requestDto);
            boardRepository.save(board);
        }

        return new BoardResponseDto(board);
    }

    @Transactional
    public void deleteBoard(long boardId, User user) {
        Board board = findBoardById(boardId);

        if (board.getUser().getId() == user.getId() || checkSenderInvited(user, board)) {
            boardRepository.delete(board);
        }
    }

    @Transactional
    public void inviteToBoard(long boardId, InviteBoardRequestDto requestDto, User sender) {
        Board board = findBoardById(boardId);

        User receiver = userService.findByUsername(requestDto.getUsername());

        if (sender.getUsername().equals(receiver.getUsername())) {
            throw new BusinessException(CANNOT_INVITE_MYSELF);
        }

        checkReceiverInvited(receiver, board);

        if (board.getUser().getId() == sender.getId() || checkSenderInvited(sender, board)) {
            InvitedBoard invitedBoard = new InvitedBoard(sender, receiver, board);
            invitedBoardRepository.save(invitedBoard);
        }
    }

    @Transactional(readOnly = true)
    public List<BoardResponseDto> getAllInvitedBoards(User user) {
        List<Board> boardList = invitedBoardRepository.findAllInvitedBoards(user);
        return boardList.stream().map(BoardResponseDto::new).toList();
    }

    public boolean checkSenderInvited(User sender, Board board) {
        InvitedBoard invitedBoard = invitedBoardRepository.findByBoardAndReceiver(board, sender);
        if (invitedBoard == null) {
            throw new BusinessException(BOARD_NOT_INVITED);
        }
        return true;
    }

    public void checkReceiverInvited(User receiver, Board board) {
        InvitedBoard invitedBoard = invitedBoardRepository.findByBoardAndReceiver(board, receiver);
        if (invitedBoard != null) {
            throw new BusinessException(BOARD_ALREADY_INVITED);
        }
    }

    public Board findBoardById(long boardId) {
        return boardRepository.findById(boardId).orElseThrow(
                () -> new BusinessException(BOARD_NOT_FOUND)
        );
    }
}
