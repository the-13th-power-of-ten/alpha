package com.sparta.tentrillion.board.repository;

import com.sparta.tentrillion.board.entity.Board;
import com.sparta.tentrillion.user.entity.User;

import java.util.List;

public interface BoardQRepository {

    List<Board> findAllInvitedBoards(User user);
}
