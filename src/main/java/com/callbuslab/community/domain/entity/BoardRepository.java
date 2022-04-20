package com.callbuslab.community.domain.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("select p from Board p where p.delAble <> 0 order by p.id desc")
    List<Board> findAllDesc();
}
