package com.blaxsior.board.web.auth;

import com.blaxsior.board.domain.auth.dto.FindUserInfoDto;
import com.blaxsior.board.domain.auth.dto.ResetPasswordDto;
import com.blaxsior.board.domain.auth.dto.SignupDto;
import com.blaxsior.board.domain.auth.exception.InvalidResetPasswordTokenException;
import com.blaxsior.board.domain.auth.service.AuthService;
import com.blaxsior.board.domain.auth.exception.PasswordConfirmNotMatchException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private String indexUrl;

    @Value("${current.indexUrl}")
    public void setIndexUrl(String indexUrl) {
        this.indexUrl = indexUrl;
    }


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
        if (result.hasErrors()) {
            return "auth/signupPage";
        }

        try {
            authService.signup(signupDto);
        } catch (PasswordConfirmNotMatchException exception) {
            result.reject("PasswordConfirmNotMatch", exception.getMessage());
        }

        if (result.hasErrors()) {
            return "auth/signupPage";
        }

        return "redirect:/";
    }

    @GetMapping("/notice")
    public String notice() {
        return "auth/noticePage";
    }

    @GetMapping("/find-id")
    public String findIdPage() {
        return "auth/findIdPage";
    }

    @PostMapping("/find-id")
    public String findIdPage(@Valid @ModelAttribute FindUserInfoDto dto, BindingResult result) {
        if (result.hasErrors()) {
            return "auth/findIdPage";
        }

        authService.mailToFindUsername(dto.getEmail(), indexUrl);
        return "redirect:/auth/notice";
    }

    @GetMapping("/find-password")
    public String findPasswordPage() {
        return "auth/findPasswordPage";
    }

    @PostMapping("/find-password")
    public String findPassword(@Valid @ModelAttribute FindUserInfoDto dto, BindingResult result) {
        if (result.hasErrors()) {
            return "auth/findPasswordPage";
        }

        String link = indexUrl + "/auth/reset-password?token=";
        try {
            authService.mailToResetPassword(dto.getEmail(), link);
        }catch (RuntimeException e) {
            // 이메일이 일치하지 않더라도 별도로 설명 X
        }

        return "redirect:/auth/notice";
    }

    @GetMapping("/reset-password")
    public String resetPasswordPage(@RequestParam("token") String token, Model model) {
        model.addAttribute("resetPasswordForm", new ResetPasswordDto());
        model.addAttribute("token", token);
        return "auth/resetPasswordPage";
    }

    @PostMapping("/reset-password")
    public String resetPassword(
            @RequestParam("token") String token,
            @Valid @ModelAttribute("resetPasswordForm") ResetPasswordDto resetPasswordDto,
            BindingResult result) {
        if (result.hasErrors()) {
            return "auth/resetPasswordPage";
        }
        try {
            authService.resetPassword(token, resetPasswordDto);
        } catch (PasswordConfirmNotMatchException ex1) {
            result.reject("PasswordConfirmNotMatch", ex1.getMessage());
        } catch (InvalidResetPasswordTokenException ex2) {
            return "auth/resetPasswordFailPage";
        }

        return "auth/resetPasswordSuccessPage";
    }
}
