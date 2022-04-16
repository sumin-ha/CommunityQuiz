package com.callbuslab.community.domain.entity;

import com.callbuslab.community.domain.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 게시글의 좋아요 히스토리 엔티티
 */
@NoArgsConstructor
@Entity
@Getter
public class Favorite extends BaseEntity {

    // 댓글의 고유 id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 좋아요 찍은 글의 id
    @Column(nullable = false)
    private Long boardId;

    // 좋아요 찍은 사람의 id
    @Column(nullable = false)
    private Long memberId;

    @Builder
    public Favorite(Long boardId, Long memberId) {
        this.boardId = boardId;
        this.memberId = memberId;
    }
}
