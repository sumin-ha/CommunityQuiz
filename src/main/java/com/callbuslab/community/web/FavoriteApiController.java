package com.callbuslab.community.web;

import com.callbuslab.community.service.FavoriteService;
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

    private final FavoriteService favoriteService;

    // 글 좋아요 누르기
    // TODO 유저 id 습득 부분 작성 필요
    @PostMapping("api/favorite/enable")
    public int favoriteEnable(Model model, @RequestParam String id) {
        return favoriteService.enableFavorite(Long.parseLong(id), 0L);
    }
}
