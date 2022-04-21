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

    // 댓글 id
    private Long commentId;
    // 댓글 쓴 사람 닉네임
    private String commentWriter;
    // 댓글 내용
    private String content;

    @Builder
    public BoardCommentDto(Long commentId, String commentWriter, String content) {
        this.commentId = commentId;
        this.commentWriter = commentWriter;
        this.content = content;
    }
}
