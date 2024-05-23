package com.blaxsior.board.web.auth.service;

import com.blaxsior.board.domain.member.entity.Member;
import com.blaxsior.board.web.auth.dto.SignupDto;

// 로그인 / 로그아웃 기능은 시큐리티가 알아서 처리해줌
public interface AuthService {
    void signup(SignupDto signupDto);
}
