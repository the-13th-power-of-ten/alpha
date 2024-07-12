package com.sparta.tentrillion.board.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.tentrillion.board.entity.Board;
import com.sparta.tentrillion.user.User;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static com.sparta.tentrillion.board.entity.QBoard.board;
import static com.sparta.tentrillion.board.entity.QInvitedBoard.invitedBoard;

@RequiredArgsConstructor
public class BoardQRepositoryImpl implements BoardQRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Board> findAllInvitedBoards(User user) {
        List<Board> invitedList = queryFactory.select(board)
                .from(board)
                .join(invitedBoard).on(invitedBoard.board.eq(board))
                .fetchJoin()
                .where(invitedBoard.receiver.id.eq(user.getId()))
                .orderBy(board.createdAt.desc())
                .fetch();

        List<Board> createdList = queryFactory.select(board)
                .from(board)
                .where(board.user.eq(user))
                .orderBy(board.createdAt.desc())
                .fetch();

        return Stream.of(invitedList, createdList).flatMap(Collection::stream).toList();
    }
}
