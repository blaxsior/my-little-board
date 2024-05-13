package com.blaxsior.board.auth.service;

import com.blaxsior.board.member.entity.Member;

public interface AuthService {
    /**
     * 로그인에 사용되는 메서드
     * @param loginId
     * @param password
     * @return
     */
    Member login(String loginId, String password);
}
