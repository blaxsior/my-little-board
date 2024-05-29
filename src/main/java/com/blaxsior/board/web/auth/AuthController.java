package com.blaxsior.board.web.auth;

import com.blaxsior.board.domain.auth.dto.FindUserInfoDto;
import com.blaxsior.board.domain.auth.dto.SignupDto;
import com.blaxsior.board.domain.auth.service.AuthService;
import com.blaxsior.board.domain.auth.exception.PasswordConfirmNotMatchException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @GetMapping("/login")
    public String loginPage() {
        return "auth/loginPage";
    }

    @GetMapping("/signup")
    public String signupPage(Model model) {
        // 객체 매핑 편리하기 위한 목적
        model.addAttribute("signupform", new SignupDto());

        return "auth/signupPage";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("signupform") SignupDto signupDto, BindingResult result) {
        if(result.hasErrors()) {
            return "auth/signupPage";
        }

        try {
            authService.signup(signupDto);
        } catch(PasswordConfirmNotMatchException exception) {
            result.reject("PasswordConfirmNotMatch", exception.getMessage());
        }

        if(result.hasErrors()) {
            return "auth/signupPage";
        }

        return "redirect:/";
    }
    @GetMapping("/cnotice")
    public String notice() {
        return "auth/noticePage";
    }

    @GetMapping("/find-id")
    public String findIdPage() {
        return "auth/findIdPage";
    }

    @PostMapping("/find-id")
    public String findIdPage(@Valid @ModelAttribute FindUserInfoDto dto, BindingResult result) {
        if(result.hasErrors()) {
            return "auth/findIdPage";
        }

        authService.findUsername(dto.getEmail());
        return "redirect:/auth/notice";
    }
    @GetMapping("/find-password")
    public String findPasswordPage() {
        return "auth/findPasswordPage";
    }

    @PostMapping("/find-password")
    public String findPassword(@Valid @ModelAttribute FindUserInfoDto dto, BindingResult result) {
        if(result.hasErrors()) {
            return "auth/findPasswordPage";
        }
        return "redirect:/auth/notice";
    }
}
