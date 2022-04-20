package com.callbuslab.community.web.dto;

import com.callbuslab.community.constraint.ResultObject;
import com.callbuslab.community.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 멤버과 관련된 api 요청을 수신하는 컨트롤러
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("api/member")
public class MemberApiController {

    private final MemberService memberService;

    // 멤버 등록
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
