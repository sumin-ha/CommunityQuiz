package com.callbuslab.community.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 글 상세 보기용 DTO
 */
@Getter
@NoArgsConstructor
public class BoardDto {

    // 글 고유 id
    private Long id;
    // 글 제목
    private String title;
    // 글 내용
    private String content;
    // 글 쓴 시간
    private String createdDate;
    // 글의 마지막 수정시간
    private String modifiedDate;
    // 글의 삭제시간
    private String deleteDate;
    // 좋아요 수
    private Integer favoriteCount;
    // 글쓴이
    private String writer;
    // 좋아요 체크여부 (개인)
    private Boolean favoriteFlag;
    // 글에 좋아요를 한 사람의 리스트
    private List<String> favoriteCheckerList;
    // 글에 달린 댓글의 리스트
    private List<BoardCommentDto> boardCommentList;

    @Builder
    public BoardDto(Long id, String title, String content, String createdDate, String modifiedDate, String deleteDate, Integer favoriteCount, String writer, Boolean favoriteFlag, List<String> favoriteCheckerList, List<BoardCommentDto> boardCommentList) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.deleteDate = deleteDate;
        this.favoriteCount = favoriteCount;
        this.writer = writer;
        this.favoriteFlag = favoriteFlag;
        this.favoriteCheckerList = favoriteCheckerList;
        this.boardCommentList = boardCommentList;
    }
}
