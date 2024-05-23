package com.blaxsior.board.web.auth.controller;

import com.blaxsior.board.web.auth.exception.PasswordConfirmNotMatchException;
import com.blaxsior.board.web.auth.service.AuthService;
import com.blaxsior.board.web.auth.dto.SignupDto;
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
}
