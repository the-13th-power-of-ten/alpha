package com.sparta.tentrillion.board.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.tentrillion.board.entity.Board;
import com.sparta.tentrillion.user.entity.User;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.sparta.tentrillion.board.entity.QBoard.board;
import static com.sparta.tentrillion.board.entity.QInvitedBoard.invitedBoard;

@RequiredArgsConstructor
public class BoardQRepositoryImpl implements BoardQRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Board> findAllInvitedBoards(User user) {
        return queryFactory.select(board)
                .from(board)
                .leftJoin(invitedBoard).on(invitedBoard.board.eq(board))
                .fetchJoin()
                .where(invitedBoard.receiver.id.eq(user.getId())
                        .or(board.user.eq(user)))
                .orderBy(board.createdAt.desc())
                .fetch();
    }
}
