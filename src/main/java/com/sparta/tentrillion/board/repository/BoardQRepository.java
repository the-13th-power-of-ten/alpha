package com.sparta.tentrillion.board.repository;

import com.sparta.tentrillion.board.entity.Board;
import com.sparta.tentrillion.user.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardQRepository {

    List<Board> findAllInvitedBoards(User user);
}
