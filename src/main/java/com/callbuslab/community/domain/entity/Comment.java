package com.callbuslab.community.domain.entity;

import com.callbuslab.community.domain.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

/**
 * 게시글의 댓글 엔티티
 */
@NoArgsConstructor
@Entity
@Getter
public class Comment extends BaseEntity {

    // 댓글

    // 댓글의 고유 id
    // 댓글 작성자의 id
    // 댓글이 달린 글의 id
    // 댓글의 작성시간
    // 댓글의 마지막 수정시간
    // 댓글의 삭제시간
    // 댓글의 내용
    // 댓글의 삭제여부
}
