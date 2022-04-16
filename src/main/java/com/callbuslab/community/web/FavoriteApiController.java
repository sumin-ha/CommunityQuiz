package com.callbuslab.community.web;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 좋아요와 관련된 api 요청을 수신하는 컨트롤러
 */
@RequiredArgsConstructor
@RestController
public class FavoriteApiController {

    // 글 좋아요 누르기
    @PostMapping("api/favorite/enable")
    public Long favoriteEnable(Model model, @RequestParam String id) {
        return null;
    }

    // 글 좋아요 해제하기
    @PostMapping("api/favorite/disable")
    public Long favoriteDisable(Model model, @RequestParam String id) {
        return null;
    }
}
