package com.callbuslab.community.web;

import com.callbuslab.community.web.dto.CommentWriteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 댓글과 관련된 api 요청을 수신하는 컨트롤러
 */
@RequiredArgsConstructor
@RestController
public class CommentApiController {

    // 댓글 쓰기
    @PostMapping("api/comment/register")
    public Long commentRegister(Model model, @RequestBody CommentWriteDto dto) {
        return null;
    }

    // 댓글 수정
    @PostMapping("api/comment/update")
    public Long commentUpdate(Model model, @RequestBody CommentWriteDto dto) {
        return null;
    }

    // 댓글 삭제
    @PostMapping("api/comment/delete")
    public Long commentDelete(Model model, @RequestParam String dto) {
        return null;
    }
}
