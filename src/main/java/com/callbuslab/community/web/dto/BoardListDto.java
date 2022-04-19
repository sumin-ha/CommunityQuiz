package com.callbuslab.community.web.dto;

import lombok.Builder;

/**
 * 글 목록용 DTO
 */
public class BoardListDto {

    // 글 고유 id
    private String id;
    // 글 제목
    private String title;
    // 글쓴이
    private String writer;
    // 좋아요 여부(개인화)
    private Boolean favoriteFlag;
    // 좋아요 수
    private String favoriteCount;

    @Builder
    public BoardListDto(String id, String title, String writer, Boolean favoriteFlag, String favoriteCount) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.favoriteFlag = favoriteFlag;
        this.favoriteCount = favoriteCount;
    }
}
