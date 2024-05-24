package com.blaxsior.board.domain.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupDto {
    @NotBlank
    private String username;
    // 패턴은 CHATGPT 도움 받음. 7자리 이상, 공백 X. 최소 1개의 특수문자 포함
    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z0-9])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{7,}$", message = "invalid password")
    private String password;

    @NotBlank
    private String passwordConfirm;

    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String nickname;
}
