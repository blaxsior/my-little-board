package com.blaxsior.board.domain.auth.service;

import com.blaxsior.board.domain.auth.dto.ResetPasswordDto;
import com.blaxsior.board.domain.auth.dto.SignupDto;

// 로그인 / 로그아웃 기능은 시큐리티가 알아서 처리해줌
public interface AuthService {
    String mailToResetPassword(String email, String indexUrl);

    void resetPassword(String token, ResetPasswordDto dto);

    void mailToFindUsername(String email, String indexUrl);

    void signup(SignupDto signupDto);
}
