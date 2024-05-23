package com.blaxsior.board.domain.member.service;

import com.blaxsior.board.domain.member.entity.Member;
import com.blaxsior.board.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void create(String username, String password, String nickname, String email) {
        var member = new Member();
        member.setUsername(username);
        member.setEmail(email);
        member.setNickname(nickname);
        member.setPassword(password);

        memberRepository.save(member);
    }

    @Override
    @Transactional
    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    @Override
    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    @Override
    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }
}
