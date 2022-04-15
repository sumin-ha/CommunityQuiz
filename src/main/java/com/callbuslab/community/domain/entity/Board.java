package com.callbuslab.community.domain.entity;

import com.callbuslab.community.domain.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
    // 글 내용
    // 글 작성자
    // 글의 좋아요 수
    // 글의 작성시간
    // 글의 마지막 수정시간
    // 글의 삭제시간
    // 글의 삭제여부

}
