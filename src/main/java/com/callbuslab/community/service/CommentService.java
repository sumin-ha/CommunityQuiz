package com.callbuslab.community.service;

import com.callbuslab.community.constraint.DeleteFlag;
import com.callbuslab.community.constraint.ResponseMessage;
import com.callbuslab.community.domain.entity.Comment;
import com.callbuslab.community.domain.entity.CommentRepository;
import com.callbuslab.community.web.dto.CommentWriteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 댓글 관련 서비스 클래스
 */
@RequiredArgsConstructor
@Service
public class CommentService {

    /** 댓글 레포지토리 */
    private final CommentRepository commentRepository;

    /**
     * 댓글 등록
     *
     * <p>
     *     댓글 신규 등록
     * </p>
     *
     * @param boardId 댓글 등록 대상 글 id
     * @param dto 댓글 내용
     * @param memberId 이용자 id
     * @return 처리 결과 메세지
     */
    public String registerComment(Long boardId, CommentWriteDto dto, Long memberId) {

        // 댓글 등록용 객체생성
        Comment comment = Comment.builder().commentBoardId(boardId)
                .commentWriterId(memberId)
                .content(dto.getContent())
                .delAble(DeleteFlag.NOT_DELETE.getDeleteFlag())
                .build();

        // 등록
        Comment resultEntity = commentRepository.save(comment);

        // 결과 반환
        if(resultEntity != null) {
            return ResponseMessage.successMessage;
        } else {
            return ResponseMessage.commentRegisterFail;
        }
    }

    /**
     * 댓글 수정
     *
     * <p>
     *     댓글 수정<br>
     *     존재하지 않는 글 id거나 해당 글의 글쓴이가 아니라면 에러
     * </p>
     *
     * @param dto 수정 댓글 내용
     * @param commentId 수정 대상 댓글 id
     * @param memberId 이용자 id
     * @return 처리 결과 메세지
     */
    public String updateComment(CommentWriteDto dto, Long commentId, Long memberId) {

        // 댓글 등록 여부 확인
        Comment targetEntity = commentRepository.getById(commentId);

        // 댓글 id로 습득이 되지 않거나, 해당 유저가 쓴 댓글이 아닐 경우 에러
        if(targetEntity == null || targetEntity.getCommentWriterId() != memberId) {
            return ResponseMessage.authError;
        }

        // 댓글 수정용 객체생성
        targetEntity.setUpdateContent(dto.getContent());

        // 수정 등록
        Comment resultEntity = commentRepository.save(targetEntity);

        // 결과 반환
        if(resultEntity != null) {
            return ResponseMessage.successMessage;
        } else {
            return ResponseMessage.commentUpdateFail;
        }
    }

    /**
     * 댓글 삭제
     *
     * <p>
     *     댓글 삭제<br>
     *     존재하지 않는 글 id거나 해당 글의 글쓴이가 아니라면 에러
     * </p>
     *
     * @param commentId 삭제 대상 댓글 id
     * @param memberId 이용자 id
     * @return 처리 결과 메세지
     */
    public String deleteComment(Long commentId, Long memberId) {

        // 댓글 등록 여부 확인
        Comment targetEntity = commentRepository.getById(commentId);

        // 댓글 id로 습득이 되지 않거나, 해당 유저가 쓴 댓글이 아닐 경우 에러
        if(targetEntity == null || targetEntity.getCommentWriterId() != memberId) {
            return ResponseMessage.authError;
        }

        // 댓글 삭제용 파라메터 세팅
        targetEntity.setDeleteContent(LocalDateTime.now());

        // 삭제 등록
        // 논리 삭제를 하기때문에, 삭제여부와 삭제시간을 업데이트
        Comment resultEntity = commentRepository.save(targetEntity);

        // 결과 반환
        if(resultEntity != null) {
            return ResponseMessage.successMessage;
        } else {
            return ResponseMessage.commentDeleteFail;
        }
    }
}
