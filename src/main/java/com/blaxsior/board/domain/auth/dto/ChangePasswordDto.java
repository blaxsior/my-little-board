package com.blaxsior.board.domain.auth.dto;

import com.blaxsior.board.domain.auth.validator.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChangePasswordDto {
    @NotBlank
    String beforePassword;

    @NotBlank
    @ValidPassword
    String newPassword;

    @NotBlank
    String newPasswordConfirm;
}
