package com.callbuslab.community.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 등록 할 댓글의 정보를 담는 DTO
 */
@Getter
@NoArgsConstructor
public class CommentWriteDto {

    // 댓글 고유 id (수정용)
    private String id;
    // 글 id
    private String boardId;
    // 댓글 내용
    private String content;
}
