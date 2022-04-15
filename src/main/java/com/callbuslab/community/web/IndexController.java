package com.callbuslab.community.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 화면 컨트롤러
 */
@RequiredArgsConstructor
@Controller
public class IndexController {

    // 접속 메인 페이지
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
