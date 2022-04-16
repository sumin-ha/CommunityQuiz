package com.callbuslab.community.web;

import com.callbuslab.community.web.dto.BoardWriteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 글과 관련된 api 요청을 수신하는 컨트롤러
 */
@RequiredArgsConstructor
@RestController
public class BoardApiController {

    // 글 목록 호출
    @GetMapping("api/board/list")
    public Long boardList() {
        return null;
    }

    // 글 등록
    @PostMapping("api/board/register")
    public Long boardRegister(Model model, @RequestBody BoardWriteDto dto) {
        return null;
    }

    // 글 수정
    @PostMapping("api/board/update")
    public Long boardUpdate(Model model, @RequestBody BoardWriteDto dto) {
        return null;
    }

    // 글 삭제
    @PostMapping("api/board/delete")
    public Long boardDelete(Model model, @RequestParam String id) {
        return null;
    }
}
