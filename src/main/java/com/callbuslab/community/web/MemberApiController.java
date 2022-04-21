package com.callbuslab.community.web;

import com.callbuslab.community.constraint.ResultObject;
import com.callbuslab.community.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 구성원과 관련된 api 요청을 수신하는 컨트롤러
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("api/member")
public class MemberApiController {

    /** 구성원 서비스 */
    private final MemberService memberService;

    /**
     * 닉네임 등록 API
     *
     * <p>
     *     커뮤니티에 처음 온 사람이 글, 댓글 등록을 위해 필요한 닉네임 등록 API<br>
     *     닉네임 등록을 통해, 커뮤니티용으로 쓰이는 회원 테이블에 등록하고 글 등록과 댓글 등록이 가능해지는 구조
     * </p>
     *
     * @param nickName 닉네임
     * @param authorization HttpHeader authorization 정보
     * @return
     */
    @GetMapping("/{nickName}")
    public ResponseEntity registerNickName(@PathVariable String nickName, @RequestHeader String authorization) {

        // 닉네임 등록 처리
        String message = memberService.registerNickName(nickName, authorization);

        // 반환 객체 생성
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResultObject resultObject = ResultObject.builder().status(HttpStatus.OK).message(message).build();
        return ResponseEntity.ok().headers(headers).body(resultObject);
    }
}
