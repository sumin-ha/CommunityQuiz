package com.callbuslab.community.domain.entity;

import com.callbuslab.community.domain.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 게시글의 댓글 엔티티
 */
@NoArgsConstructor
@Entity
@Getter
public class Comment extends BaseEntity {

    // 댓글

    // 댓글의 고유 id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 댓글 작성자의 id
    @Column(nullable = false)
    private Long commentWriterId;

    // 댓글이 달린 글의 id
    @Column(nullable = false)
    private Long commentBoardId;

    // 댓글의 내용
    @Column(nullable = false)
    private Long content;

    // 댓글의 삭제시간
    @Column
    private LocalDateTime deleteDate;

    // 댓글의 삭제여부
    @Column
    private String delAble;

    @Builder
    public Comment(Long commentWriterId, Long commentBoardId, Long content, String delAble) {
        this.commentWriterId = commentWriterId;
        this.commentBoardId = commentBoardId;
        this.content = content;
        this.delAble = delAble;
    }

    // update, delete용 id세팅
    public void setUpdateId(Long id) {
        this.id = id;
    }
}
