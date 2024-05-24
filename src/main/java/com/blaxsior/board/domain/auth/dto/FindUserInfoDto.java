package com.blaxsior.board.domain.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindUserInfoDto {
    @NotBlank
    @Email
    private String email;
}
