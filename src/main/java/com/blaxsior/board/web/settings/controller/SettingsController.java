package com.blaxsior.board.web.settings.controller;

import com.blaxsior.board.domain.member.entity.Member;
import com.blaxsior.board.web.loginmember.LoginMember;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/settings")
@Slf4j
public class SettingsController {
    @GetMapping("/mypage")
    public String myPage(@LoginMember Member member, Model model) {
        log.info("member: {}", member);
        model.addAttribute("member", member);
        return "/settings/myPage";
    }

}
