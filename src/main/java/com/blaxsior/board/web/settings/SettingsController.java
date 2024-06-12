package com.blaxsior.board.web.settings;

import com.blaxsior.board.domain.auth.dto.ChangePasswordDto;
import com.blaxsior.board.domain.auth.service.AuthService;
import com.blaxsior.board.domain.member.entity.Member;
import com.blaxsior.board.domain.member.annotation.LoginMember;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Slf4j

@Controller
@RequestMapping("/settings")
@RequiredArgsConstructor
public class SettingsController {
    private final AuthService authService;
    @GetMapping("/mypage")
    public String myPage(@LoginMember Member member, Model model) {
        log.info("member: {}", member);
        model.addAttribute("member", member);
        return "settings/myPage";
    }
    @GetMapping("/change-password")
    public String changePasswordPage(@ModelAttribute("changePasswordForm") ChangePasswordDto dto) {
        return "/settings/changePasswordPage";
    }

    @PostMapping("/change-password")
    public String changePassword(@LoginMember Member member,
                                 @Valid @ModelAttribute("changePasswordForm") ChangePasswordDto dto, BindingResult result) {
        // 로그인 한 유저가 없으면 안됨. security에 의해 현재 경로는 로그인해야만 접근 가능.
        if(result.hasErrors()) {
            return "/settings/changePasswordPage";
        }

        try {
            authService.changePassword(member.getId(), dto);
        }
        catch(Exception e) {
            String errMessage = e.getMessage();
            result.reject(null, errMessage);
            return "/settings/changePasswordPage";
        }

        return "/";
    }
}
