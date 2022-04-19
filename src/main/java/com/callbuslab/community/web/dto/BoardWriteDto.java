package com.callbuslab.community.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 등록 할 글의 정보를 담는 DTO
 */
@Getter
@NoArgsConstructor
public class BoardWriteDto {

    // 글 제목
    private String title;
    // 글 내용
    private String content;
}
