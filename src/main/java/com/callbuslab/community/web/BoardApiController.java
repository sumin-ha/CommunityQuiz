package com.callbuslab.community.web;

import com.callbuslab.community.constraint.ResponseMessage;
import com.callbuslab.community.constraint.ResultObject;
import com.callbuslab.community.service.BoardService;
import com.callbuslab.community.service.MemberService;
import com.callbuslab.community.web.dto.BoardDto;
import com.callbuslab.community.web.dto.BoardListDto;
import com.callbuslab.community.web.dto.BoardWriteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    private final MemberService memberService;

    // 글 목록 호출
    @GetMapping("/")
    public ResponseEntity boardList(@RequestHeader(required = false) String authorization) {

        // 현재 로그인 중인 멤버 ID 습득
        Long memberId = memberService.getMemberId(authorization);

        // 습득 요청
        List<BoardListDto> list = boardService.getBoardList(memberId);

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
    @GetMapping("/{boardId}")
    public ResponseEntity boardDetail(@RequestHeader(required = false) String authorization,
                                      @PathVariable Long boardId) {

        // 현재 로그인 중인 멤버 ID 습득
        Long memberId = memberService.getMemberId(authorization);

        // 습득 요청
        BoardDto boardDto = boardService.getBoard(boardId, memberId);

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
    @PostMapping("/")
    public ResponseEntity boardRegister(@RequestHeader String authorization, @RequestBody BoardWriteDto dto) {

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
            message = boardService.registerBoard (dto, memberId);
        }

        // 반환 객체 생성
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResultObject resultObject = ResultObject.builder().status(HttpStatus.OK).message(message).build();
        return ResponseEntity.ok().headers(headers).body(resultObject);
    }

    // 글 수정
    @PutMapping("/{boardId}")
    public ResponseEntity boardUpdate(@RequestHeader String authorization,
                                      @PathVariable Long boardId,
                                      @RequestBody BoardWriteDto dto) {

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
            message = boardService.updateBoard(dto, boardId, memberId);
        }

        // 반환 객체 생성
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResultObject resultObject = ResultObject.builder().status(HttpStatus.OK).message(message).build();
        return ResponseEntity.ok().headers(headers).body(resultObject);
    }

    // 글 삭제
    @DeleteMapping("/{boardId}")
    public ResponseEntity boardDelete(@RequestHeader String authorization, @PathVariable Long boardId) {

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
            message = boardService.deleteBoard(boardId, memberId);
        }

        // 반환 객체 생성
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResultObject resultObject = ResultObject.builder().status(HttpStatus.OK).message(message).build();
        return ResponseEntity.ok().headers(headers).body(resultObject);
    }
}
