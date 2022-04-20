package com.callbuslab.community.domain.entity;

import com.callbuslab.community.constraint.DeleteFlag;
import com.callbuslab.community.domain.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 게시글 엔티티
 */
@NoArgsConstructor
@Entity
@Getter
public class Board extends BaseEntity {

    // 글의 고유 id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 글 제목
    @Column(nullable = false)
    private String title;

    // 글 내용
    @Column(nullable = false)
    private String content;

    // 글 작성자
    @Column(nullable = false)
    private Long writerId;

    // 글의 좋아요 수
    @Column
    private Integer favoriteCount;

    // 글의 삭제시간
    @Column
    private LocalDateTime deleteDate;

    // 글의 삭제여부
    @Column
    private String delAble;

    @Builder
    public Board(String title, String content, Long writerId, Integer favoriteCount, String delAble) {
        this.title = title;
        this.content = content;
        this.writerId = writerId;
        this.favoriteCount = favoriteCount;
        this.delAble = delAble;
    }

    // 수정 할 컨텐츠 세팅
    public void setUpdateContent(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // 삭제용 컨텐츠 세팅
    public void setDeleteContent(LocalDateTime deleteDate) {
        this.deleteDate = deleteDate;
        this.delAble = DeleteFlag.DELETE.getDeleteFlag();
    }

    // 좋아요 용 카운트 세팅
    public void incrementFavoriteCount() {
        this.favoriteCount++;
    }
}
