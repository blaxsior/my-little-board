package com.blaxsior.board.auth.controller;

import com.blaxsior.board.auth.dto.LoginForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @GetMapping("/login")
    public String loginPage(@ModelAttribute("loginForm") LoginForm loginForm) {
        return "auth/loginPage";
    }

    @PostMapping("/login")
    public String login(
            @Valid @ModelAttribute LoginForm loginForm,
            BindingResult result,
            @RequestParam(name = "redirectUrl", required = false) String redirectUrl
    ) {
        if(result.hasErrors()) {
            return "redirect:auth/login";
        }

        if(redirectUrl != null) {
            return "redirect:" + redirectUrl;
        }

        return "redirect:/";
    }
}
