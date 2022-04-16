package com.callbuslab.community.domain.entity;

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
    // 글은 작성 수정 삭제가 가능하다.
    // 글은 좋아요수를 가지고 있다.

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
    private String writer;

    // 글의 좋아요 수
    @Column
    private String favoriteCount;

    // 글의 삭제시간
    @Column
    private LocalDateTime deleteDate;

    // 글의 삭제여부
    @Column
    private String delAble;

    @Builder
    public Board(String title, String content, String writer, String favoriteCount, String delAble) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.favoriteCount = favoriteCount;
        this.delAble = delAble;
    }

    // update, delete용 id세팅
    public void setUpdateId(Long id) {
        this.id = id;
    }
}
