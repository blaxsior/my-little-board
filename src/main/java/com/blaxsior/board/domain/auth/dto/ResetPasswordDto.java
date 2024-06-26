package com.blaxsior.board.domain.auth.dto;

import com.blaxsior.board.domain.auth.validator.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResetPasswordDto {
    @NotBlank
    @ValidPassword
    private String password;

    @NotBlank
    private String passwordConfirm;
}
