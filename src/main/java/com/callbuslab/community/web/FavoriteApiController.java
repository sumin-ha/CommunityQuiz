package com.callbuslab.community.web;

import com.callbuslab.community.constraint.ResultObject;
import com.callbuslab.community.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 좋아요와 관련된 api 요청을 수신하는 컨트롤러
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("api/favorite")
public class FavoriteApiController {

    private final FavoriteService favoriteService;

    // 글 좋아요 누르기
    // TODO 유저 id 습득 부분 작성 필요
    @GetMapping("/{boardId}")
    public ResponseEntity favoriteEnable(Model model, @PathVariable Long boardId) {

        // 작업 실행
        String resultMessage = favoriteService.enableFavorite(boardId, 0L);

        // 응답 객체 생성
        ResultObject resultObject = ResultObject.builder()
                .status(HttpStatus.OK)
                .message(resultMessage)
                .build();
        return ResponseEntity.ok().body(resultObject);
    }
}
