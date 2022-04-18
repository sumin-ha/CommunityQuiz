package com.callbuslab.community.web;

import com.callbuslab.community.service.CommentService;
import com.callbuslab.community.web.dto.CommentWriteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 댓글과 관련된 api 요청을 수신하는 컨트롤러
 */
@RequiredArgsConstructor
@RestController
public class CommentApiController {

    private final CommentService commentService;

    // 댓글 쓰기
    // TODO 유저 id 습득 부분 작성 필요
    @PostMapping("api/comment/register")
    public int commentRegister(Model model, @RequestBody CommentWriteDto dto) {
        return commentService.registerComment(dto, 0L);
    }

    // 댓글 수정
    // TODO 유저 id 습득 부분 작성 필요
    @PostMapping("api/comment/update")
    public int commentUpdate(Model model, @RequestBody CommentWriteDto dto) {
        return commentService.updateComment(dto, 0L);
    }

    // 댓글 삭제
    // TODO 유저 id 습득 부분 작성 필요
    @PostMapping("api/comment/delete")
    public int commentDelete(Model model, @RequestBody CommentWriteDto dto) {
        return commentService.deleteComment(dto, 0L);
    }
}
