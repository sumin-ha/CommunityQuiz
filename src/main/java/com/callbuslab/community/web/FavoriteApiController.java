package com.callbuslab.community.web;

import com.callbuslab.community.constraint.ResponseMessage;
import com.callbuslab.community.constraint.ResultObject;
import com.callbuslab.community.service.FavoriteService;
import com.callbuslab.community.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 좋아요와 관련된 api 요청을 수신하는 컨트롤러
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("api/favorite")
public class FavoriteApiController {

    private final FavoriteService favoriteService;
    private final MemberService memberService;

    // 글 좋아요 누르기
    @GetMapping("/{boardId}")
    public ResponseEntity favoriteEnable(@RequestHeader String authorization, @PathVariable Long boardId) {

        // 현재 로그인 중인 멤버 ID 습득
        Long memberId = memberService.getMemberId(authorization);

        // 반환용 메세지
        String message = "";

        // 좋아요 처리는 임차인, 임대인, 공인중개사만 가능함.
        // 습득한 memberId가 Long의 MIN_VALUE라면 외부인 혹은 닉네임 미등록자이므로 처리 불가
        if(memberId == Long.MIN_VALUE) {
            // 처리 불가 메세지
            message = ResponseMessage.memberNotFoundError;
        } else {
            // 좋아요 처리
            message = favoriteService.enableFavorite(boardId, memberId);
        }

        // 응답 객체 생성
        ResultObject resultObject = ResultObject.builder()
                .status(HttpStatus.OK)
                .message(message)
                .build();
        return ResponseEntity.ok().body(resultObject);
    }
}
