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

    /** 글 서비스 */
    private final BoardService boardService;
    /** 구성원 서비스 */
    private final MemberService memberService;

    /**
     * 게시글 목록 취득 API
     *
     * <p>
     *     게시판 글 목록을 취득함. <br>
     *     좋아요 체크 여부 파악을 위해 authorization 정보로 이용자 id를 습득하여 목록 취득.<br>
     *     임차인, 임대인, 공인중개사, 외부이용자 모두 이용가능.
     * </p>
     *
     * @param authorization HttpHeader authorization 정보
     * @return
     */
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

    /**
     * 게시글 상세 취득 API
     *
     * <p>
     *     요청 받은 글 번호에 해당하는 글 상세를 습득하는 API<br>
     *     좋아요 체크 여부 파악을 위해 authorization 정보로 이용자 id를 습득하여 목록 취득.<br>
     *     임차인, 임대인, 공인중개사, 외부이용자 모두 이용가능.
     * </p>
     *
     * @param authorization HttpHeader authorization 정보
     * @param boardId 글 id
     * @return
     */
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

    /**
     * 게시글 등록
     *
     * <p>
     *    게시글 등록하는 API<br>
     *    임대인, 임차인, 공인중개사만 이용가능
     * </p>
     *
     * @param authorization HttpHeader authorization 정보
     * @param dto 등록 할 글 내용
     * @return
     */
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
            message = boardService.registerBoard(dto, memberId);
        }

        // 반환 객체 생성
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResultObject resultObject = ResultObject.builder().status(HttpStatus.OK).message(message).build();
        return ResponseEntity.ok().headers(headers).body(resultObject);
    }

    /**
     * 게시글 수정
     *
     * <p>
     *    게시글 수정하는 API<br>
     *    임대인, 임차인, 공인중개사만 이용가능
     * </p>
     *
     * @param authorization HttpHeader authorization 정보
     * @param boardId 수정 대상 글 id
     * @param dto
     * @return
     */
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

    /**
     * 게시글 삭제
     *
     * <p>
     *    게시글 삭제하는 API<br>
     *    임대인, 임차인, 공인중개사만 이용가능
     * </p>
     *
     * @param authorization HttpHeader authorization 정보
     * @param boardId 삭제 대상 글 id
     * @return
     */
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
