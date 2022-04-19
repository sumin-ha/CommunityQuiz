package com.callbuslab.community.web.dto;

import lombok.Builder;

/**
 * 글 상세 보기의 댓글용 DTO
 */
public class BoardCommentDto {

    private Long commentId;
    private String commentWriter;
    private String content;

    @Builder
    public BoardCommentDto(Long commentId, String commentWriter, String content) {
        this.commentId = commentId;
        this.commentWriter = commentWriter;
        this.content = content;
    }
}
