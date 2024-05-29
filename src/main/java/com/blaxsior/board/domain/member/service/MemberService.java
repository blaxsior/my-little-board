package com.blaxsior.board.domain.member.service;

import com.blaxsior.board.domain.member.entity.Member;

import java.util.Optional;

public interface MemberService {
    void create(String username,  String password,String nickname, String email);
    Optional<Member> findById(Long id);

    void save(Member member);
    Optional<Member> findByUsername(String username);
    Optional<Member> findByEmail(String username);
    boolean checkExistByEmail(String email);
    void deleteById(Long id);
}
