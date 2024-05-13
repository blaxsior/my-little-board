package com.blaxsior.board.auth.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginForm {
        @NotNull(message = "아이디가 필요합니다.")
        private String username;
        @NotNull(message = "비밀번호가 필요합니다.")
        private String password;
}
