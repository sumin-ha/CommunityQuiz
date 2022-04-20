package com.callbuslab.community.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 글 상세 보기의 댓글용 DTO
 */
@Getter
@NoArgsConstructor
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
