package com.callbuslab.community.domain.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select p from Favorite p where p.boardId = :boardId and p.memberId = :memberId")
    Favorite findByBoardIdAndMemberId(@Param(value = "boardId")Long boardId, @Param(value = "memberId")Long memberId);
}
