package com.callbuslab.community.domain.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    @Query("select p from Favorite p where p.boardId = :boardId and p.memberId = :memberId")
    Favorite findByBoardIdAndMemberId(@Param(value = "boardId")Long boardId, @Param(value = "memberId")Long memberId);

    @Query("select p from Favorite p where p.memberId = :memberId")
    List<Favorite> findByMemberId(@Param(value = "memberId")Long memberId);

    @Query("select p from Favorite p where p.boardId = :boardId")
    List<Favorite> findByBoardId(@Param(value = "boardId")Long boardId);
}
