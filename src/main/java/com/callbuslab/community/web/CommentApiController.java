package com.callbuslab.community.web;

import com.callbuslab.community.constraint.ResponseMessage;
import com.callbuslab.community.constraint.ResultObject;
import com.callbuslab.community.service.CommentService;
import com.callbuslab.community.service.MemberService;
import com.callbuslab.community.web.dto.CommentWriteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 댓글과 관련된 api 요청을 수신하는 컨트롤러
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("api/comment")
public class CommentApiController {

    /** 댓글 서비스 */
    private final CommentService commentService;
    /** 구성원 서비스 */
    private final MemberService memberService;

    /**
     * 댓글 등록 API
     *
     * <p>
     *    댓글 등록하는 API<br>
     *    임대인, 임차인, 공인중개사만 이용가능
     * </p>
     *
     * @param authorization HttpHeader authorization 정보
     * @param boardId 댓글 등록 대상 글 id
     * @param dto 댓글 내용
     * @return
     */
    @PostMapping("/{boardId}")
    public ResponseEntity commentRegister(@RequestHeader String authorization,
                                          @PathVariable Long boardId,
                                          @RequestBody CommentWriteDto dto) {

        // 현재 로그인 중인 멤버 ID 습득
        Long memberId = memberService.getMemberId(authorization);

        // 반환용 메세지
        String message = "";

        // 등록 처리는 임차인, 임대인, 공인중개사만 가능함.
        // 습득한 memberId가 Long의 MIN_VALUE라면 외부인 혹은 닉네임 미등록자이므로 처리 불가
        if(memberId == Long.MIN_VALUE) {
            // 에러 메세지
            message = ResponseMessage.memberNotFoundError;
        } else {
            // 등록 처리
            message = commentService.registerComment(boardId, dto, memberId);
        }

        // 반환 객체 생성
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResultObject resultObject = ResultObject.builder().status(HttpStatus.OK).message(message).build();
        return ResponseEntity.ok().headers(headers).body(resultObject);
    }

    /**
     * 댓글 수정 API
     *
     * <p>
     *    댓글 수정하는 API<br>
     *    임대인, 임차인, 공인중개사만 이용가능
     * </p>
     *
     * @param authorization HttpHeader authorization 정보
     * @param commentId 수정 대상 댓글 id
     * @param dto 수정 댓글 내용
     * @return
     */
    @PutMapping("/{commentId}")
    public ResponseEntity commentUpdate(@RequestHeader String authorization,
                                        @PathVariable Long commentId,
                                        @RequestBody CommentWriteDto dto) {

        // 현재 로그인 중인 멤버 ID 습득
        Long memberId = memberService.getMemberId(authorization);

        // 반환용 메세지
        String message = "";

        // 수정 처리는 임차인, 임대인, 공인중개사만 가능함.
        // 습득한 memberId가 Long의 MIN_VALUE라면 외부인 혹은 닉네임 미등록자이므로 처리 불가
        if(memberId == Long.MIN_VALUE) {
            // 에러 메세지
            message = ResponseMessage.memberNotFoundError;
        } else {
            // 수정 처리
            message = commentService.updateComment(dto, commentId, memberId);
        }

        // 반환 객체 생성
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResultObject resultObject = ResultObject.builder().status(HttpStatus.OK).message(message).build();
        return ResponseEntity.ok().headers(headers).body(resultObject);
    }

    /**
     * 댓글 삭제 API
     *
     * <p>
     *    댓글 삭제하는 API<br>
     *    임대인, 임차인, 공인중개사만 이용가능
     * </p>
     *
     * @param authorization HttpHeader authorization 정보
     * @param commentId 삭제 대상 댓글 id
     * @return
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity commentDelete(@RequestHeader String authorization, @PathVariable Long commentId) {

        // 현재 로그인 중인 멤버 ID 습득
        Long memberId = memberService.getMemberId(authorization);

        // 반환용 메세지
        String message = "";

        // 삭제 처리는 임차인, 임대인, 공인중개사만 가능함.
        // 습득한 memberId가 Long의 MIN_VALUE라면 외부인 혹은 닉네임 미등록자이므로 처리 불가
        if(memberId == Long.MIN_VALUE) {
            // 에러 메세지
            message = ResponseMessage.memberNotFoundError;
        } else {
            // 삭제 처리
            message = commentService.deleteComment(commentId, memberId);
        }

        // 반환 객체 생성
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResultObject resultObject = ResultObject.builder().status(HttpStatus.OK).message(message).build();
        return ResponseEntity.ok().headers(headers).body(resultObject);
    }
}
