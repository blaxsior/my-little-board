package com.blaxsior.board.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    // 메인 페이지
    @GetMapping("/")
    public String home() {
        return "index";
    }
}
