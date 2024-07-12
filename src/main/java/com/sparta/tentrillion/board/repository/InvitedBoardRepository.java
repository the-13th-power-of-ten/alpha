package com.sparta.tentrillion.board.repository;

import com.sparta.tentrillion.board.entity.Board;
import com.sparta.tentrillion.board.entity.InvitedBoard;
import com.sparta.tentrillion.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitedBoardRepository extends JpaRepository<InvitedBoard, Long>, BoardQRepository {

    InvitedBoard findByBoardAndSenderAndReceiver(Board board, User sender, User receiver);

    InvitedBoard findByBoardAndReceiver(Board board, User sender);
}
