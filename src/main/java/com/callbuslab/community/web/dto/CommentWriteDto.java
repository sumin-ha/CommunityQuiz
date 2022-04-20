package com.callbuslab.community.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 등록 할 댓글의 정보를 담는 DTO
 */
@Getter
@NoArgsConstructor
public class CommentWriteDto {

    // 댓글 내용
    private String content;
}
