package com.callbuslab.community.domain.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select p from Comment p where p.commentBoardId = :commentBoardId")
    List<Comment> findByBoardId(@Param(value = "commentBoardId")Long commentBoardId);
}
