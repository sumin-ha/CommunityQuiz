package com.callbuslab.community.web;

import com.callbuslab.community.constraint.ResponseMessage;
import com.callbuslab.community.constraint.ResultObject;
import com.callbuslab.community.domain.entity.Board;
import com.callbuslab.community.service.BoardService;
import com.callbuslab.community.web.dto.BoardDto;
import com.callbuslab.community.web.dto.BoardListDto;
import com.callbuslab.community.web.dto.BoardWriteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

        // 습득 요청
        List<BoardListDto> list = boardService.getBoardList(0L);

        // 반환 객체 생성
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResultObject resultObject = ResultObject.builder().status(HttpStatus.OK)
                .message(ResponseMessage.successMessage)
                .data(list)
                .build();
        return ResponseEntity.ok().headers(headers).body(resultObject);
    }

    // 글 본문 호출
    // TODO 유저 id 습득 부분 작성 필요
    @GetMapping("/{boardId}")
    public ResponseEntity boardDetail(Model model, @PathVariable Long boardId) {

        // 습득 요청
        BoardDto boardDto = boardService.getBoard(boardId,0L);

        // 반환 객체 생성
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResultObject resultObject = ResultObject.builder().status(HttpStatus.OK)
                .message(ResponseMessage.successMessage)
                .data(boardDto)
                .build();
        return ResponseEntity.ok().headers(headers).body(resultObject);
    }

    // 글 등록
    // TODO 유저 id 습득 부분 작성 필요
    @PostMapping("/")
    public ResponseEntity boardRegister(Model model, @RequestBody BoardWriteDto dto) {

        String message = boardService.registerBoard (dto, 0L);

        // 반환 객체 생성
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResultObject resultObject = ResultObject.builder().status(HttpStatus.OK).message(message).build();
        return ResponseEntity.ok().headers(headers).body(resultObject);
    }

    // 글 수정
    // TODO 유저 id 습득 부분 작성 필요
    @PutMapping("/{boardId}")
    public ResponseEntity boardUpdate(Model model, @PathVariable Long boardId, @RequestBody BoardWriteDto dto) {

        String message = boardService.updateBoard(dto, boardId, 0L);

        // 반환 객체 생성
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResultObject resultObject = ResultObject.builder().status(HttpStatus.OK).message(message).build();
        return ResponseEntity.ok().headers(headers).body(resultObject);
    }

    // 글 삭제
    // TODO 유저 id 습득 부분 작성 필요
    @DeleteMapping("/{boardId}")
    public ResponseEntity boardDelete(Model model, @PathVariable Long boardId) {

        String message = boardService.deleteBoard(boardId, 0L);

        // 반환 객체 생성
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResultObject resultObject = ResultObject.builder().status(HttpStatus.OK).message(message).build();
        return ResponseEntity.ok().headers(headers).body(resultObject);
    }
}
