package com.callbuslab.community.web;

import com.callbuslab.community.constraint.ResultObject;
import com.callbuslab.community.service.CommentService;
import com.callbuslab.community.web.dto.CommentWriteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 댓글과 관련된 api 요청을 수신하는 컨트롤러
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("api/comment")
public class CommentApiController {

    private final CommentService commentService;

    // 댓글 쓰기
    // TODO 유저 id 습득 부분 작성 필요
    @PostMapping("/")
    public ResponseEntity commentRegister(Model model, @RequestBody CommentWriteDto dto) {

        String message = commentService.registerComment(dto, 0L);

        // 반환 객체 생성
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResultObject resultObject = ResultObject.builder().status(HttpStatus.OK).message(message).build();
        return ResponseEntity.ok().headers(headers).body(resultObject);
    }

    // 댓글 수정
    // TODO 유저 id 습득 부분 작성 필요
    @PutMapping("/{commentId}")
    public ResponseEntity commentUpdate(Model model, @PathVariable Long commentId, @RequestBody CommentWriteDto dto) {

        String message = commentService.updateComment(dto, commentId, 0L);

        // 반환 객체 생성
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResultObject resultObject = ResultObject.builder().status(HttpStatus.OK).message(message).build();
        return ResponseEntity.ok().headers(headers).body(resultObject);
    }

    // 댓글 삭제
    // TODO 유저 id 습득 부분 작성 필요
    @DeleteMapping("/{commentId}")
    public ResponseEntity commentDelete(Model model, @PathVariable Long commentId) {

        String message = commentService.deleteComment(commentId, 0L);

        // 반환 객체 생성
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResultObject resultObject = ResultObject.builder().status(HttpStatus.OK).message(message).build();
        return ResponseEntity.ok().headers(headers).body(resultObject);
    }
}
