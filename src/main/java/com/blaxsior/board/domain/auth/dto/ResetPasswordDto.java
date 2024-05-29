package com.blaxsior.board.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResetPasswordDto {
    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z0-9])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{7,}$", message = "invalid password")
    private String password;

    @NotBlank
    private String passwordConfirm;
}
