package com.callbuslab.community.domain.entity;

import com.callbuslab.community.domain.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

/**
 * 게시글의 좋아요 히스토리 엔티티
 */
@NoArgsConstructor
@Entity
@Getter
public class Favorite extends BaseEntity {

    // 좋아요 찍은 글의 id
    // 좋아요 찍은 사람의 id
}
