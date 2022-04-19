package com.callbuslab.community.web;

import com.callbuslab.community.service.BoardService;
import com.callbuslab.community.web.dto.BoardListDto;
import com.callbuslab.community.web.dto.BoardWriteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 글과 관련된 api 요청을 수신하는 컨트롤러
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("api/board")
public class BoardApiController {

    private final BoardService boardService;

    // 글 목록 호출
    // TODO 유저 id 습득 부분 작성 필요
    @GetMapping("/")
    public ResponseEntity boardList(Model model) {
        List<BoardListDto> list = boardService.getBoardList(0L);
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return ResponseEntity.ok().headers(headers).body(list);
    }

    // 글 본문 호출
    // TODO 유저 id 습득 부분 작성 필요
    @GetMapping("/{boardId}")
    public ResponseEntity boardDetail(Model model, @PathVariable Long boardId) {
        model.addAttribute("detail", boardService.getBoard(boardId,0L));
        return null;
    }

    // 글 등록
    // TODO 유저 id 습득 부분 작성 필요
    @PostMapping("/")
    public int boardRegister(Model model, @RequestBody BoardWriteDto dto) {
        return boardService.registerBoard(dto, 0L);
    }

    // 글 수정
    // TODO 유저 id 습득 부분 작성 필요
    @PutMapping("/{boardId}")
    public int boardUpdate(Model model, @PathVariable Long boardId, @RequestBody BoardWriteDto dto) {
        return boardService.updateBoard(dto, boardId, 0L);
    }

    // 글 삭제
    // TODO 유저 id 습득 부분 작성 필요
    @DeleteMapping("/{boardId}")
    public int boardDelete(Model model, @PathVariable Long boardId) {
        return boardService.deleteBoard(boardId, 0L);
    }
}
